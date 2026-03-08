import threading
import requests
import random
import string
import time
from datetime import datetime

BASE_URL = "http://localhost:8080/api/auth"

FIELDS_OF_INTEREST = ["Technology", "Science", "Arts", "Sports", "Music"]
USERS = []
LOCK = threading.Lock()

total_success = 0
total_failed  = 0

# ── helpers ────────────────────────────────────────────────────────────────────

def random_string(length=8):
    return ''.join(random.choices(string.ascii_lowercase, k=length))

def log(thread_id, message):
    ts = datetime.now().strftime("%H:%M:%S.%f")[:-3]
    print(f"[{ts}] [Thread-{thread_id}] {message}")

# ── step 1: create 200 users ───────────────────────────────────────────────────

def create_users(total=200):
    print(f"\n{'='*60}")
    print(f"  Creating {total} users...")
    print(f"{'='*60}\n")

    success, failed = 0, 0

    for i in range(1, total + 1):
        name     = f"user_{random_string(6)}_{i}"
        password = random_string(10)
        payload  = {
            "name":             name,
            "email":            f"{name}@test.com",
            "password":         password,
            "level":            random.randint(1, 10),
            "FieldsOfInterest": random.choice(FIELDS_OF_INTEREST),
            "Profilepicture":   "not available",
        }

        try:
            resp = requests.post(f"{BASE_URL}/register", params=payload, timeout=10)
            if resp.status_code == 201:
                USERS.append({"name": name, "password": password})
                success += 1
                print(f"  [+] ({i:>3}/{total}) Created  → {name}")
            else:
                failed += 1
                print(f"  [-] ({i:>3}/{total}) Failed   → {name} | {resp.status_code} {resp.text[:80]}")
        except requests.RequestException as e:
            failed += 1
            print(f"  [!] ({i:>3}/{total}) Error    → {name} | {e}")

    print(f"\n  Done: {success} created, {failed} failed\n")

# ── step 2: 3 threads log in their share of users ─────────────────────────────

def login_worker(thread_id, users_chunk):
    log(thread_id, f"Starting — will log in {len(users_chunk)} users")
    ok, fail = 0, 0

    for user in users_chunk:
        try:
            resp = requests.post(
                f"{BASE_URL}/login",
                params={"name": user["name"], "password": user["password"]},
                timeout=10
            )
            if resp.status_code == 200:
                token = resp.json().get("token", "")[:30]
                ok += 1
                log(thread_id, f"  ✓ {user['name']} → token: {token}…")
            else:
                fail += 1
                log(thread_id, f"  ✗ {user['name']} → {resp.status_code} {resp.text[:60]}")
        except requests.RequestException as e:
            fail += 1
            log(thread_id, f"  ! {user['name']} → {e}")

    log(thread_id, f"Done — {ok} ok, {fail} failed")

    with LOCK:
        global total_success, total_failed
        total_success += ok
        total_failed  += fail

# ── main ───────────────────────────────────────────────────────────────────────

if __name__ == "__main__":
    create_users(200)

    if not USERS:
        print("No users were created — aborting login phase.")
        exit(1)

    NUM_THREADS = 3
    # round-robin split: thread 1 → users 0,3,6,…  thread 2 → 1,4,7,…  etc.
    chunks = [USERS[i::NUM_THREADS] for i in range(NUM_THREADS)]

    print(f"\n{'='*60}")
    print(f"  Logging in {len(USERS)} users across {NUM_THREADS} threads")
    print(f"  Split: {[len(c) for c in chunks]} users per thread")
    print(f"{'='*60}\n")

    threads = []
    start = time.time()

    for idx, chunk in enumerate(chunks, start=1):
        t = threading.Thread(target=login_worker, args=(idx, chunk), daemon=True)
        threads.append(t)
        t.start()

    for t in threads:
        t.join()

    print(f"\n{'='*60}")
    print(f"  Login phase complete in {time.time() - start:.2f}s")
    print(f"  Total success : {total_success}")
    print(f"  Total failed  : {total_failed}")
    print(f"{'='*60}\n")
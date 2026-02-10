import Image from "next/image";
export default function Logo() {
  return (
    <a
      className="w-full flex justify-center p-3 absolute top-0"
      href="https://www.atec.pt/"
    >
      <Image src={"/atec_logo.png"} width={40} height={40} alt="logo" />
    </a>
  );
}

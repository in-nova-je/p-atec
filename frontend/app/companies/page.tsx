"use client";

import SearchBar from "@/components/SearchBar";
import { useEffect, useState } from "react";
import CompanyCard from "@/components/CompanyCard";
import { type Enterprise } from "@/lib/types";
import { getAllEnterprises } from "@/lib/actions/enterprise";
import CompanyCardSkeleton from "@/components/skeletons/CompanyCardSkeleton";

export default function Companies() {
  const [companies, setCompanies] = useState<Enterprise[]>([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [filteredCompanies, setFilteredCompanies] = useState<Enterprise[]>(
    companies as Enterprise[],
  );
  useEffect(() => {
    const fetchCompanies = async () => {
      const data = (await getAllEnterprises()) as Enterprise[];
      setCompanies(data);
      setLoading(false);
    };
    fetchCompanies();
  }, []);

  useEffect(() => {
    const filtered = companies.filter((company) =>
      company.name.toLowerCase().includes(search.toLowerCase()),
    );
    setFilteredCompanies(
      (search !== "" ? filtered : companies) as Enterprise[],
    );
  }, [search, companies]);
  return (
    <>
      <SearchBar setSearch={setSearch} />
      <div className="flex font-sans p-4 mb-16 flex-col">
        <div className="flex flex-col">
          <h1 className="mb-2">Empresas</h1>
          <p className="text-secondary">
            Encontra aqui as empresas que estão presentes na feira do trabalho
            da ATEC!
          </p>
        </div>
        <hr className="text-secondary/25 my-5" />
        <div className="flex flex-col gap-4">
          {loading ? (
            <>
              <CompanyCardSkeleton />
              <CompanyCardSkeleton />
              <CompanyCardSkeleton />
              <CompanyCardSkeleton />
              <CompanyCardSkeleton />
            </>
          ) : (
            filteredCompanies.map((company: Enterprise) => (
              <CompanyCard
                key={company.name}
                name={company.name}
                logo={"data:image/png;base64," + company.profilePicture}
                description={company.description}
              />
            ))
          )}
        </div>
      </div>
    </>
  );
}

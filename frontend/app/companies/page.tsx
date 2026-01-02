"use client";

import SearchBar from "@/components/SearchBar";
import { useEffect, useState } from "react";
import companies from "@/json/companies.json";
import CompanyCard from "@/components/CompanyCard";

export default function Companies() {
  const [search, setSearch] = useState("");
  const [filteredCompanies, setFilteredCompanies] = useState(companies);
  useEffect(() => {
    const filtered = companies.filter((company) =>
      company.name.toLowerCase().includes(search.toLowerCase())
    );
    setFilteredCompanies(search !== "" ? filtered : companies);
  }, [search]);
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
          {filteredCompanies.map((company) => (
            <CompanyCard
              key={company.name}
              name={company.name}
              logo={company.logoPath}
              description={company.description}
            />
          ))}
        </div>
      </div>
    </>
  );
}

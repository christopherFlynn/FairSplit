import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import Button from "./Button";

interface Props {
  children: React.ReactNode;
}

export default function Layout({ children }: Props) {
  const { token, logout } = useAuth();
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-gray-50 text-gray-900">
      <header className="bg-white shadow-sm px-6 py-4 flex justify-between items-center border-b">
        <h1
          onClick={() => navigate("/dashboard")}
          className="text-xl font-bold text-blue-600 cursor-pointer"
        >
          FairSplit
        </h1>
        {token && (
          <Button variant="danger" onClick={logout}>
            Logout
          </Button>
        )}
      </header>

      <main className="max-w-3xl mx-auto p-4">{children}</main>
    </div>
  );
}

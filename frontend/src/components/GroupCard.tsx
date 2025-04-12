import React from "react";
import { useNavigate } from "react-router-dom";
import Button from "./Button";
import { ArrowRightIcon } from "@heroicons/react/24/solid";

interface GroupCardProps {
  group: {
    id: string;
    name: string;
    inviteCode: string;
  };
}

export default function GroupCard({ group }: GroupCardProps) {
  const navigate = useNavigate();

  return (
    <div className="bg-white p-4 rounded shadow hover:shadow-md transition">
      <h3 className="text-lg font-bold text-gray-800">{group.name}</h3>
      <p className="text-sm text-gray-500 mb-2">
        Invite Code: {group.inviteCode}
      </p>
      <Button
        onClick={() => navigate(`/groups/${group.id}`)}
        className="text-blue-600 text-sm hover:underline"
      >
        View Group <ArrowRightIcon className="w-4 h-4 inline-block ml-1" />
      </Button>
    </div>
  );
}

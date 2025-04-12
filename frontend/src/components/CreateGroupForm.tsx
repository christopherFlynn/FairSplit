import React from "react";
import { useState } from "react";
import { createGroup } from "../api/groups";
import Button from "./Button";
import { PlusIcon } from "@heroicons/react/24/solid";

interface Props {
  onCreate: () => void;
}

export default function CreateGroupForm({ onCreate }: Props) {
  const [groupName, setGroupName] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await createGroup(groupName);
      setGroupName("");
      setError("");
      onCreate();
    } catch {
      setError("Failed to create group");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="mb-6 space-y-2">
      <input
        type="text"
        placeholder="Group name"
        className="p-2 border rounded w-full"
        value={groupName}
        onChange={(e) => setGroupName(e.target.value)}
      />
      <Button
        type="submit"
        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
      >
        <PlusIcon className="w-4 h-4 inline-block mr-2" />
        Create Group
      </Button>
      {error && <p className="text-red-500">{error}</p>}
    </form>
  );
}

import React from "react";
import { useState } from "react";
import { joinGroup } from "../api/groups";
import Button from "./Button";
import { UserPlusIcon } from "@heroicons/react/24/solid";

interface Props {
  onJoin: () => void;
}

export default function JoinGroupForm({ onJoin }: Props) {
  const [inviteCode, setInviteCode] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleJoin = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await joinGroup(inviteCode);
      setInviteCode("");
      setSuccess("Joined group!");
      setError("");
      onJoin();
    } catch {
      setError("Invalid invite code or already joined");
      setSuccess("");
    }
  };

  return (
    <form onSubmit={handleJoin} className="mb-6 space-y-2">
      <input
        type="text"
        placeholder="Invite code"
        className="p-2 border rounded w-full"
        value={inviteCode}
        onChange={(e) => setInviteCode(e.target.value)}
      />
      <Button
        type="submit"
        className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
      >
        <UserPlusIcon className="w-4 h-4 inline-block mr-2" />
        Join Group
      </Button>
      {error && <p className="text-red-500">{error}</p>}
      {success && <p className="text-green-600">{success}</p>}
    </form>
  );
}

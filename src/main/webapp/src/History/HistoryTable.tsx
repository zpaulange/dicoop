import { Divider, Space, Title } from "@mantine/core";
import { CommitteeSet } from "src/Model/CommitteeSet";
import SolutionTable from "src/Solution/SolutionTable";

type HistoryTableProps = {
  history: Array<CommitteeSet>;
};

function HistoryTable({ history }: HistoryTableProps) {
  console.log(history);
  
  return (
    <>
      {history.map((committees) => (
        <div key={committees.id}>
          <Title order={3}>
            <>Solution {committees.date}</>
          </Title>
          <SolutionTable committees={committees} />
          <Space h="xl" />
          <Divider />
          <Space h="xl" />
        </div>
      ))}
    </>
  );
}

export default HistoryTable;

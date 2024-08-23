import { Table, Text } from "@mantine/core";
import React from "react";
import { useTranslation } from "react-i18next";
import { CommitteeSet } from "src/Model/CommitteeSet";
import { SolvedCommittee } from "src/Model/SolvedCommittee";

type SolutionTableProps = {
  committees: CommitteeSet;
};

function SolutionTable({ committees }: SolutionTableProps) {
  const { t } = useTranslation();
  /* committees.getCommittees().map((committee: SolvedCommittee)=> {
    console.log(committee);
  }) */
  console.log(committees.getCommittees());
  
  

  const assignmentsList = (assignments: any) => (
    <React.Fragment>
      {assignments
        .filter((assignment: any) => assignment.assignedPerson)
        .map((assignment: any) => (
          <td
            role="cell"
            data-label="Assignments"
            key={assignment.assignedPerson.name}
          >
            {assignment.assignedPerson.name}
            {assignment.assignedPerson.personType && (
              <>
                <br />
                <Text size="xs" color="dimmed">
                  {t(
                    `participant.${assignment.assignedPerson.personType?.name}`
                  )}
                </Text>
              </>
            )}
          </td>
        ))}
    </React.Fragment>
  );
  //console.log(committees)
  return (
    <Table highlightOnHover aria-label="Solution" id="table-basic">
      <thead>
        <tr role="row">
          <th role="columnheader" scope="col">
            {t("solution.evaluatedPerson")}
          </th>
          <th role="columnheader" scope="col">
            {t("solution.timeslot")}
          </th>
          <th role="columnheader" scope="col">
            {t("solution.assignments")}
          </th>
        </tr>
      </thead>
      <tbody>
        {committees.getCommittees().map((committee: SolvedCommittee) => (
          (committee.evaluatedPerson?.personType?.name==='professional') ?  (
            <tr role="row" key={committee.id}>
              <td role="cell" data-label="Evaluated Person">
                {committee.evaluatedPerson?.name}
              </td>

              <td role="cell" data-label="Timeslot">
                {committee.findNTimeslotsInCommon(2)}
              </td>
              {assignmentsList(committee.getAssignments())}
            </tr>
          ):''
          
        ))}
      </tbody>
    </Table>
  );
}

export default SolutionTable;

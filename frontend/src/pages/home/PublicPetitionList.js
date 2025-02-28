import React, { useState, useEffect } from "react";
import styled from "styled-components";
import GetAllPetitionService from "../petitioner/dashboard/service/GetAllPetitionService.js";

const PublicPetitionList = () => {
  const [petitions, setPetitions] = useState([]);
  const [selectedPetition, setSelectedPetition] = useState(null);
  const [selectedStatus, setSelectedStatus] = useState("All");
  const [error, setError] = useState(null);

  const status = ["All", "Open", "Closed"];

  useEffect(() => {
    const fetchPetitions = async (status) => {
      try {
        const response = await GetAllPetitionService.getAllPetitions(status);
        if (!response.ok) {
          throw new Error("No Petition Found");
        }
        const data = await response.json();
        setPetitions(data.petitions);
        setError(null);
      } catch (err) {
        setError(err.message);
      }
    };

    fetchPetitions(selectedStatus);
  }, [selectedStatus]);

  const handleViewClick = (petition) => {
    console.log("Selected Petition: ", petition);
    setSelectedPetition(petition);
  };

  const closeModal = () => {
    setSelectedPetition(null);
  };

  return (
    <PetitionListContainer>
      <ListTitle>{selectedStatus} Petitions</ListTitle>
      <ToggleButtonGroup>
        {status.map((status) => (
          <ToggleButton
            key={status}
            active={selectedStatus === status}
            onClick={() => setSelectedStatus(status)}
          >
            {status}
          </ToggleButton>
        ))}
      </ToggleButtonGroup>

      {error ? (
        <ErrorMessage>{error}</ErrorMessage>
      ) : (
        <TableWrapper>
          <Table>
            <thead>
              <tr>
                <TableHeader>Id</TableHeader>
                <TableHeader>Date</TableHeader>
                <TableHeader>Title</TableHeader>
                <TableHeader>Signatures Received</TableHeader>
                <TableHeader>Status</TableHeader>
                <TableHeader>More Information</TableHeader>
              </tr>
            </thead>
            <tbody>
              {petitions.map((petition) => (
                <TableRow key={petition.petition_id}>
                  <TableCell>{petition.petition_id}</TableCell>
                  <TableCell>{petition.petition_date}</TableCell>
                  <TableCell>{petition.petition_title}</TableCell>
                  <TableCell>{petition.signature}</TableCell>
                  <TableCell>{petition.status}</TableCell>
                  <TableCell>
                    <ViewButton onClick={() => handleViewClick(petition)}>View</ViewButton>
                  </TableCell>
                </TableRow>
              ))}
            </tbody>
          </Table>
        </TableWrapper>
      )}

      {selectedPetition && (
        <>
          <Overlay onClick={closeModal} />
          <Modal>
            <h3>Petition Details</h3>
            <FormContainer>
              <FormRow>
                <Label>ID:</Label>
                <Input type="text" value={selectedPetition.petition_id || "N/A"} readOnly />
              </FormRow>
              <FormRow>
                <Label>Title:</Label>
                <Input type="text" value={selectedPetition.petition_title || "N/A"} readOnly />
              </FormRow>
              <FormRow>
                <Label>Content:</Label>
                <TextArea value={selectedPetition.petition_text || "N/A"} readOnly />
              </FormRow>
              <FormRow>
                <Label>Petitioner:</Label>
                <Input type="text" value={selectedPetition.petitioner || "N/A"} readOnly />
              </FormRow>
              <FormRow>
                <Label>Result:</Label>
                <Input type="text" value={selectedPetition.response || "N/A"} readOnly />
              </FormRow>
            </FormContainer>
            <CloseButton onClick={closeModal}>Close</CloseButton>
          </Modal>
        </>
      )}
    </PetitionListContainer>
  );
};

export default PublicPetitionList;


const PetitionListContainer = styled.div`
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background-color: #121212;
  box-sizing: border-box;
  max-width: 100%;
  margin: 0 auto;
`;

const ListTitle = styled.h2`
  font-size: 32px;
  font-weight: bold;
  color: white;
  margin-bottom: 40px;
  text-align: center;
`;

const ToggleButtonGroup = styled.div`
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  gap: 10px;
`;

const ToggleButton = styled.button`
  padding: 10px 20px;
  background-color: ${(props) => (props.active ? "#007bff" : "#444")};
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: ${(props) => (props.active ? "#0056b3" : "#555")};
  }
`;

const TableWrapper = styled.div`
  max-height: 70vh; /* Set height relative to viewport for responsiveness */
  overflow-y: auto; /* Add vertical scroll */
  width: 100%;
  border: 1px solid #444;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
`;

const Table = styled.table`
  width: 100%; /* Make table take full width of its container */
  border-collapse: collapse;
  table-layout: fixed; /* Distribute space evenly among columns */
`;

const TableHeader = styled.th`
  position: sticky;
  top: 0;
  background: #333;
  z-index: 1;
  padding: 15px;
  font-size: 16px;
  color: #fff;
  text-align: left;
  border-bottom: 2px solid #444;
`;

const TableRow = styled.tr`
  &:nth-child(odd) {
    background: #2a2a2a;
  }

  &:nth-child(even) {
    background: #242424;
  }

  &:hover {
    background: #3a3a3a;
  }
`;

const TableCell = styled.td`
  padding: 15px;
  font-size: 14px;
  color: #e0e0e0;
  text-align: left;
  border-bottom: 1px solid #444;
`;

const ViewButton = styled.button`
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 14px;
  cursor: pointer;

  &:hover {
    background-color: #0056b3;
  }
`;

const Modal = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: #2a2a2a;
  color: #e0e0e0;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.5);
  z-index: 1000;
  max-width: 600px;
  width: 90%;
  text-align: left;
`;

const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  z-index: 999;
`;

const CloseButton = styled.button`
  background: #ff0000;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 14px;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px;
  &:hover {
    background: #cc0000;
  }
`;

const FormContainer = styled.form`
  display: flex;
  flex-direction: column;
  gap: 15px;
`;

const FormRow = styled.div`
  display: flex;
  align-items: center;
  gap: 20px;
`;

const Label = styled.label`
  font-weight: bold;
  color: #e0e0e0;
  min-width: 100px;
  text-align: left;
`;

const Input = styled.input`
  flex: 1;
  padding: 10px;
  border: 1px solid #444;
  border-radius: 5px;
  background-color: #2a2a2a;
  color: #e0e0e0;
  font-size: 14px;
  pointer-events: none;
`;

const TextArea = styled.textarea`
  flex: 1;
  padding: 10px;
  border: 1px solid #444;
  border-radius: 5px;
  background-color: #2a2a2a;
  color: #e0e0e0;
  font-size: 14px;
  pointer-events: none;
  resize: none;
  height: 80px;
`;

const ErrorMessage = styled.div`
  background-color: rgb(234, 130, 132);
  color: #ffffff;
  padding: 15px;
  border-radius: 5px;
  font-size: 16px;
  text-align: center;
  max-width: 600px;
  margin: 20px auto;
  box-shadow: 0 2px 10px rgba(255, 77, 79, 0.3);
`;
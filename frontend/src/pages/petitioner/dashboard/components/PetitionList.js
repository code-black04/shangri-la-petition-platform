import React, { useState, useEffect } from "react";
import styled from "styled-components";
import GetAllPetitionService from "../service/GetAllPetitionService.js";
import SignPetitionService from "../service/SignPetitionService.js";
import { getCurrentUser } from "../../utils/AuthUtils.js";

const PetitionListContainer = styled.div`
  background: #1e1e1e;
  border: 1px solid #333;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
  max-width: 900px;
  width: 90%;
  margin: 0 auto;
  color: #e0e0e0;
`;

const ListTitle = styled.h2`
  text-align: center;
  margin-bottom: 20px;
  font-size: 28px;
  font-weight: 600;
  color: #ffffff;
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
  max-height: 400px; /* Set a fixed height for the table */
  overflow-y: auto; /* Add vertical scroll */
  border: 1px solid #444;
  border-radius: 5px;
`;

const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
  position: relative;
`;

const TableHeader = styled.th`
  position: sticky;
  top: 0;
  background: #2a2a2a;
  z-index: 1;
  padding: 12px;
  font-size: 16px;
  color: #ffffff;
  text-align: left;
  border-bottom: 2px solid #444;
`;

const TableRow = styled.tr`
  &:nth-child(odd) {
    background: #242424;
  }

  &:nth-child(even) {
    background: #1e1e1e;
  }

  &:hover {
    background: #333;
  }
`;

const TableCell = styled.td`
  padding: 12px;
  font-size: 14px;
  color: #e0e0e0;
  text-align: left;
  border-bottom: 1px solid #444;
`;

const ViewButton = styled.button`
  padding: 8px 12px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s ease;

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
  gap: 20px; /* Space between label and input */
`;

const Label = styled.label`
  font-weight: bold;
  color: #e0e0e0;
  min-width: 100px; /* Fixed width for consistent alignment */
  text-align: right;
`;

const Input = styled.input`
  flex: 1; /* Take up remaining space */
  padding: 10px;
  border: 1px solid #444;
  border-radius: 5px;
  background-color: #2a2a2a;
  color: #e0e0e0;
  font-size: 14px;
  pointer-events: none; /* Make input non-editable */
`;

const TextArea = styled.textarea`
  flex: 1;
  padding: 10px;
  border: 1px solid #444;
  border-radius: 5px;
  background-color: #2a2a2a;
  color: #e0e0e0;
  font-size: 14px;
  pointer-events: none; /* Make textarea non-editable */
  resize: none;
  height: 80px; /* Adjust height as needed */
`;

const ErrorMessage = styled.div`
  background-color:rgb(234, 130, 132);
  color: #ffffff;
  padding: 15px;
  border-radius: 5px;
  font-size: 16px;
  text-align: center;
  max-width: 600px;
  margin: 20px auto;
  box-shadow: 0 2px 10px rgba(255, 77, 79, 0.3);
`;


const Notification = styled.div`
  background-color: ${(props) => (props.type === "success" ? "#4caf50" : "#f44336")};
  color: white;
  padding: 15px;
  border-radius: 5px;
  font-size: 16px;
  text-align: center;
  margin: 20px auto;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
  max-width: 600px;
  position: fixed;
  top: 10px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 1000;
`;


const PetitionList = () => {
  const [petitions, setPetitions] = useState([]);
  const [selectedPetition, setSelectedPetition] = useState(null);
  const [selectedStatus, setSelectedStatus] = useState("All");
  const [error, setError] = useState(null);

  const [notification, setNotification] = useState("");
  const [notificationType, setNotificationType] = useState("");

  const currentUserEmail = getCurrentUser();


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

  const isPetitionerSigned = (petition) => {
    return petition.petitionSigningUserEntityList.some(
      (signingUser) => signingUser.emailId === currentUserEmail
    );
  };

  const handleViewClick = (petition) => {
    setSelectedPetition(petition); // Set the clicked petition as selected
  };

  const handleSignPetition = async (petitionId) => {
    try {
      await SignPetitionService.signPetition(petitionId);

      // Update the specific petition in the local state
      setPetitions((prevPetitions) =>
        prevPetitions.map((petition) =>
          petition.petition_id === petitionId
            ? {
                ...petition,
                signature: petition.signature + 1,
                petitionSigningUserEntityList: [
                  ...petition.petitionSigningUserEntityList,
                  { petition_id: petitionId, emailId: currentUserEmail }, // Add the current user
                ],
              }
            : petition
        )
      );

      setNotification("Petition signed successfully!");
      setNotificationType("success");
      
      setSelectedStatus("All");

      setTimeout(() => setNotification(""), 3000);
    } catch (err) {
      setNotification("Failed to sign the petition.");
      setNotificationType("error");

      // Clear notification after 4 seconds
      setTimeout(() => setNotification(""), 4000);
    }
  };
  

  const closeModal = () => {
    setSelectedPetition(null); // Deselect the petition to close modal
  };

  return (
    <PetitionListContainer>
      {notification && <Notification type={notificationType}>{notification}</Notification>}
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
                <TableHeader>Signatures</TableHeader>
                <TableHeader>Status</TableHeader>
                <TableHeader>Information</TableHeader>
                <TableHeader>Signed Status</TableHeader>
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
                  <TableCell>
                    {petition.status === "Open" ? (
                      isPetitionerSigned(petition) ? (
                        <p>Signed</p>
                      ) : (
                        <ViewButton onClick={() => handleSignPetition(petition.petition_id)}>
                          Sign
                        </ViewButton>
                      )
                    ) : (
                      <p>-</p>
                    )}
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
                <Input type="text" value={selectedPetition.petition_id} readOnly />
              </FormRow>
              <FormRow>
                <Label>Title:</Label>
                <Input type="text" value={selectedPetition.petition_title} readOnly />
              </FormRow>
              <FormRow>
                <Label>Content:</Label>
                <TextArea value={selectedPetition.petition_text} readOnly />
              </FormRow>
              <FormRow>
                <Label>Petitioner:</Label>
                <Input type="text" value={selectedPetition.petitioner} readOnly />
              </FormRow>
              <FormRow>
                <Label>Result:</Label>
                <Input type="text" value={selectedPetition.response} readOnly />
              </FormRow>
            </FormContainer>
            <CloseButton onClick={closeModal}>Close</CloseButton>
          </Modal>
        </>
      )}
    </PetitionListContainer>
  );
};

export default PetitionList;

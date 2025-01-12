import React, { useState, useEffect } from "react";
import styled from "styled-components";
import GetAllPetitionService from "../service/GetAllPetitionService.js"
import { FaEdit } from "react-icons/fa"; // Install react-icons if not already installed
import PetitionDecisionService from "../service/PetitionDecisionService.js";

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
  margin-top: 20px;
  width: 100px; /* Fixed width to ensure uniform size */
  text-align: center;

  &:hover {
    background-color: #0056b3;
  }
`;

const CloseButton = styled.button`
  background: #ff0000;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px;
  &:hover {
    background: #cc0000;
  }
  width: 100px; /* Fixed width to ensure uniform size */
  text-align: center;
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
  border: 1px solid ${(props) => (props.readOnly ? "#444" : "#007bff")};
  background-color: ${(props) => (props.readOnly ? "#2a2a2a" : "#1a1a1a")};
  color: #e0e0e0;
  font-size: 14px;
  border-radius: 5px;
  &:focus {
    outline: ${(props) => (props.readOnly ? "none" : "2px solid #007bff")};
  }
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

const ReadOnlyField = styled.span`
  flex: 1;
  padding: 10px;
  background-color: #2a2a2a;
  color: #e0e0e0;
  border: 1px solid #444;
  border-radius: 5px;
  font-size: 14px;
`;

const InputWrapper = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  width: 100%;
`;

const StyledInput = styled.input`
  flex: 1;
  padding: 10px 35px 10px 10px; /* Add padding for the icon */
  border: 1px solid #444;
  border-radius: 5px;
  background-color: #2a2a2a;
  color: #e0e0e0;
  font-size: 14px;
`;

const EditIcon = styled(FaEdit)`
  position: absolute;
  right: 10px; /* Position the icon inside the input */
  color: #007bff;
  pointer-events: none; /* Prevent icon from blocking clicks */
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
`;


const PetitionList = () => {
  const [petitions, setPetitions] = useState([]);
  const [selectedPetition, setSelectedPetition] = useState(null);
  const [selectedStatus, setSelectedStatus] = useState("All");
  const [error, setError] = useState(null);
  const [notification, setNotification] = useState("");
  const [notificationType, setNotificationType] = useState("");

  const [editableStatus, setEditableStatus] = useState("");
  const [editableResult, setEditableResult] = useState("");
  const [isEditable, setIsEditable] = useState(false);

  const status = ["All", "Open", "Closed"];

  useEffect(() => {
    const fetchPetitions = async (status) => {
      try {
        const responseRecieved = await GetAllPetitionService.getAllPetitions(status);
        if (!responseRecieved.ok) {
          throw new Error("No Petition Found");
        }
        const data = await responseRecieved.json();
        setPetitions(data.petitions);
        setError(null);
      } catch (err) {
        setError(err.message);
      }
    };

    fetchPetitions(selectedStatus);
  }, [selectedStatus]);

  const handleEditClick = (petition) => {
    setSelectedPetition(petition);
    setEditableStatus(petition.status || "");
    setEditableResult(petition.response || "");
    setIsEditable(petition.status.toLowerCase() === "open");
  };


  const closeModal = () => {
    setSelectedPetition(null); // Deselect the petition to close modal
    setIsEditable(false);
  };

  const handleSave = async () => {
    const updatedPetition = {
      status: editableStatus,
      response: editableResult,
    };
  
    try {
      const data = await PetitionDecisionService.updatePetition(
        selectedPetition.petition_id,
        updatedPetition
      );

      if(data.status === 201) {
        setNotification("Petition updated successfully!"); // Success message
        setNotificationType("success");
        setTimeout(() => {
          setNotification("");
          closeModal();
        }, 2000);

        setPetitions((prev) => {
          const updatedList =  prev.map((petition) =>
            petition.petition_id === selectedPetition.petition_id
              ? { ...petition, ...data }
              : petition
          );
          return updatedList.sort((a, b) => a.petition_id - b.petition_id);
        });
      } else {
        if (data.status === 400) {
          const errorMessage = "Invalid input for Status or Result.."
          setNotification(errorMessage);
          setTimeout(() => {
            setNotification('');
          }, 3000);
          setNotificationType("error");
        } else {
          setNotification("An unexpected error occurred");
          setTimeout(() => {
            setNotification('');
          }, 3000);
          setNotificationType("error");
        }
      }
    } catch (error) {
      setNotification("An unexpected error occurred during login. Please try again.");
          setTimeout(() => {
            setNotification('');
          }, 3000);
          setNotificationType("error");
    }
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
                <TableHeader>Signatures</TableHeader>
                <TableHeader>Petition Status</TableHeader>
                <TableHeader>Actions</TableHeader>
              </tr>
            </thead>
            <tbody>
              {petitions.map((petition) => (
                <TableRow key={petition.petition_id}>
                  <TableCell>{petition.petition_id}</TableCell>
                  <TableCell>{petition.petition_date}</TableCell>
                  <TableCell>{petition.petition_title}</TableCell>
                  <TableCell>{petition.signature} / {petition.signature_threshold}</TableCell>
                  <TableCell>{petition.status}</TableCell>
                  <TableCell>
                    <ViewButton onClick={() => handleEditClick(petition)}>
                      {petition.status.toLowerCase() === "open"
                        ? "Edit"
                        : "View"}
                    </ViewButton>
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
          {notification && (
            <Notification type={notificationType}>{notification}</Notification>
          )}
          <h3>{isEditable ? "Edit Petition" : "View Petition"}</h3>
            <FormContainer>
              <FormRow>
                <Label>ID:</Label>
                <ReadOnlyField>{selectedPetition.petition_id}</ReadOnlyField>
              </FormRow>
              <FormRow>
                <Label>Title:</Label>
                <ReadOnlyField>{selectedPetition.petition_title}</ReadOnlyField>
              </FormRow>
              <FormRow>
                <Label>Content:</Label>
                <ReadOnlyField>{selectedPetition.petition_text}</ReadOnlyField>
              </FormRow>
              <FormRow>
                <Label>Petitioner:</Label>
                <ReadOnlyField>value={selectedPetition.petitioner}</ReadOnlyField>
              </FormRow>
              <FormRow>
                  <Label>Status:</Label>
                  {isEditable ? (
                    <InputWrapper>
                      <StyledInput
                        type="text"
                        value={editableStatus}
                        onChange={(e) => setEditableStatus(e.target.value)}
                      />
                      <EditIcon />
                    </InputWrapper>
                  ) : (
                    <ReadOnlyField>{editableStatus}</ReadOnlyField>
                  )}
                </FormRow>

                <FormRow>
                  <Label>Result:</Label>
                  {isEditable ? (
                    <InputWrapper>
                      <StyledInput
                        type="text"
                        value={editableResult}
                        onChange={(e) => setEditableResult(e.target.value)}
                      />
                      <EditIcon />
                    </InputWrapper>
                  ) : (
                    <ReadOnlyField>{editableResult}</ReadOnlyField>
                  )}
                </FormRow>

            </FormContainer>
            <div>
              {isEditable && <ViewButton onClick={handleSave}>Save</ViewButton>}
              <CloseButton onClick={closeModal}>Close</CloseButton>
            </div>
          </Modal>
        </>
      )}
    </PetitionListContainer>
  );
};

export default PetitionList;

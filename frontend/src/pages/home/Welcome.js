import React, { useState } from 'react';
import styled from 'styled-components';
import logo from "../../logo.svg";
import { useNavigate } from 'react-router-dom';

function Welcome() {
  const [selectedRole, setSelectedRole] = useState(null);
  const navigate = useNavigate();

  const handleRoleSelection = (role) => {
    setSelectedRole(role);
  };

  const handleContinue = () => {
    if (selectedRole === 'Petitioner') {
      navigate('/app/petitioner'); // Navigate to Petitioner page
    }
    if (selectedRole === 'Petitioner Committee') {
      navigate('/app/petitioner-committee');
    }
  };

  const handleViewPublicPetitions = () => {
    navigate('/app/open-petition-list'); // Navigate to Petition List page
  };

  return (
    <WelcomePageContainer>
      <LogoImageContainer src={logo} alt="SLPP Logo" />
      <WelcomeHeader>Welcome to Shangri-la Petition Platform</WelcomeHeader>

      <RoleSelectionContainer>
        <RoleCard
          isSelected={selectedRole === 'Petitioner'}
          onClick={() => handleRoleSelection('Petitioner')}
        >
          <RoleIcon>📜</RoleIcon>
          <RoleTitle>Petitioner</RoleTitle>
          <RoleDescription>Submit petitions and track their progress.</RoleDescription>
        </RoleCard>
        <RoleCard
          isSelected={selectedRole === 'Petitioner Committee'}
          onClick={() => handleRoleSelection('Petitioner Committee')}
        >
          <RoleIcon>👥</RoleIcon>
          <RoleTitle>Petitioner Committee</RoleTitle>
          <RoleDescription>Review and manage submitted petitions.</RoleDescription>
        </RoleCard>
      </RoleSelectionContainer>

      <ContinueButton onClick={handleContinue} disabled={!selectedRole}>
        Continue
      </ContinueButton>

      <PublicLinkSection>
        <PublicLinkButton onClick={handleViewPublicPetitions}>
          View Public Petition List
        </PublicLinkButton>
      </PublicLinkSection>

    </WelcomePageContainer>
  );
}

export default Welcome;

const WelcomePageContainer = styled.div`
  text-align: center;
  background-color: #000104;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const LogoImageContainer = styled.img`
  height: 140px;
  width: 160px;
  margin-bottom: 10px;
`;

const WelcomeHeader = styled.h1`
  font-size: 25px;
  color: white;
  margin-bottom: 60px;
`;

const RoleSelectionContainer = styled.div`
  display: flex;
  gap: 20px;
  justify-content: center;
  margin-bottom: 30px;
`;

const RoleCard = styled.div`
  width: 150px;
  padding: 20px;
  background-color: ${({ isSelected }) => (isSelected ? '#333' : '#1c1c1c')};
  border: ${({ isSelected }) => (isSelected ? '2px solid white' : 'none')};
  border-radius: 10px;
  text-align: center;
  color: white;
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover {
    transform: scale(1.05);
    background-color: ${({ isSelected }) => (isSelected ? '#333' : '#444')}
  }
`;

const RoleTitle = styled.h2`
  margin-bottom: 10px;
`;

const RoleDescription = styled.p`
  color: #bbb;
  font-size: 0.9rem;
`;

const RoleIcon = styled.div`
  font-size: 2rem;
  margin-bottom: 10px;
`;

const ContinueButton = styled.button`
  width: 150px;
  height: 40px;
  background-color: #007bff;
  color: white;
  font-size: 1rem;
  border-radius: 5px;
  cursor: pointer;
  border: none;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #0056b3;
  }

  &:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }
`;

const PublicLinkSection = styled.div`
  margin-top: 40px;
  text-align: center;
`;

const PublicLinkButton = styled.a`
  display: inline-block;
  padding: 12px 25px;
  background-color: #00d1b2;
  color: white;
  font-size: 1rem;
  font-weight: bold;
  text-decoration: none;
  border-radius: 8px;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #00a895;
  }
`;
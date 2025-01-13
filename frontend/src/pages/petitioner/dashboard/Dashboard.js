import React, { useState } from "react";
import styled from "styled-components";
import CreatePetition from "./CreatePetition";
import logo from "../../../logo.svg";
import PetitionList from "./components/PetitionList";
import Logout from "./Logout";


const Dashboard = () => {
  const [activeTab, setActiveTab] = useState("petitionList");
  const [isDropdownOpen, setIsDropdownOpen] = useState(true);

  const toggleDropdown = () => setIsDropdownOpen(!isDropdownOpen);

  const renderContent = () => {
    switch (activeTab) {
      case "petitionList":
        return <PetitionList />;
      case "createPetition":
        return <CreatePetition />;
      case "logout":
              return <Logout />;
      default:
        return null;
    }
  };

  return (
    <DashboardContainer>
      <Sidebar>
        <LogoImageContainer src={logo} alt="Logo" />
        <NavButton onClick={toggleDropdown} isActive={["petitionList", "createPetition", "proposedPetitions", "signedPetitions"].includes(activeTab)}>
          Petition
          <span>{isDropdownOpen ? "▼" : "▶"}</span>
        </NavButton>
        <SubNav $isVisible={isDropdownOpen}>
          <SubNavItem isActive={activeTab === "petitionList"} onClick={() => setActiveTab("petitionList")}>
            All Petitions
          </SubNavItem>
          <SubNavItem isActive={activeTab === "createPetition"} onClick={() => setActiveTab("createPetition")}>
            Create Petition
          </SubNavItem>
        </SubNav>

        <NavButton
          isActive={activeTab === "logout"}
          onClick={() => setActiveTab("logout")}
        >
          Logout
        </NavButton>

      </Sidebar>
      <MainContent>
        <ContentTitle>
          {activeTab === "petitionList"
            ? ""
            : activeTab === "createPetition"
            ? ""
            : ""}
        </ContentTitle>
        {renderContent()}
      </MainContent>
    </DashboardContainer>
  );
};

export default Dashboard;



const DashboardContainer = styled.div`
  display: flex;
  height: 100vh;
  background-color: #121212;
  color: #e0e0e0;
`;

const Sidebar = styled.div`
  width: 260px;
  background-color: rgb(0, 0, 0);
  border-right: 1px solid #333;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 30px 20px;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.2);
`;

export const LogoImageContainer = styled.img`
  height: 18vmin;
  margin-bottom: 20px;
  align-self: center;
`;

const NavButton = styled.div`
 width: 100%;
  padding: 12px;
  margin: 10px 0;
  background-color: transparent;
  color: ${({ isActive }) => (isActive ? "green" : "#e0e0e0")};
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  text-align: left;
  transition: all 0.3s ease;
  display: flex;
  justify-content: space-between;
  align-items: center;

  &:hover {
    color: ${({ isActive }) => (isActive ? "green" : "#007bff")};
  }
`;

const SubNav = styled.div`
  width: 100%;
  padding-left: 15px;
  margin-top: 5px;
  display: ${({ $isVisible }) => ($isVisible ? "block" : "none")};
`;

const SubNavItem = styled.div`
  padding: 8px 12px;
  margin: 5px 0;
  background-color: transparent;
  color: ${({ isActive }) => (isActive ? "green" : "#e0e0e0")};
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  text-align: left;
  transition: all 0.3s ease;

  &:hover {
    color: ${({ isActive }) => (isActive ? "#007bff" : "#007bff")};
  }
`;

const MainContent = styled.div`
  flex: 1;
  padding: 30px;
  background-color: #181818;
  border-radius: 10px;
  margin: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
`;

const ContentTitle = styled.h1`
  font-size: 24px;
  font-weight: 500;
  margin-bottom: 20px;
`;
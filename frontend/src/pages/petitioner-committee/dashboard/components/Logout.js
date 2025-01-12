import React, {useState} from "react";
import {jwtDecode} from "jwt-decode";
import styled from "styled-components";

const LogoutButton = styled.button`
  padding: 12px 24px;
  background-color: #ff4d4d;
  color: white;
  font-size: 16px;
  font-weight: bold;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  transition: background-color 0.3s ease, transform 0.2s ease;

  &:hover {
    background-color: #ff1a1a;
    transform: scale(1.05);
  }

  &:active {
    background-color: #e60000;
    transform: scale(1);
  }
`;

const ConfirmationDialog = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #2c2c2c;
  padding: 20px;
  border-radius: 10px;
  color: white;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
  text-align: center;
  width: 300px;
`;

const DialogButton = styled.button`
  padding: 10px 20px;
  margin: 10px;
  border: none;
  border-radius: 6px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;

  ${({ confirm }) =>
    confirm
      ? `
    background-color: #4caf50;
    color: white;

    &:hover {
      background-color: #45a049;
    }
  `
      : `
    background-color: #ff4d4d;
    color: white;

    &:hover {
      background-color: #ff1a1a;
    }
  `}
`;

const Logout = () => {

  const [showDialog, setShowDialog] = useState(false);

  const handleLogout = () => {
  const getCookie = (cookieName) => {
    const cookies = document.cookie.split("; ");
    for (let cookie of cookies) {
      const [name, value] = cookie.split("=");
      if (name === cookieName) {
        return decodeURIComponent(value);
      }
    }
    return null; // Return null if the cookie is not found
  };

  // Usage
  const accessToken = getCookie("accessToken");
  console.log("Access Token:", accessToken);
    var name = "";
try {
        const decoded = jwtDecode(accessToken);
        name = decoded.sub;
      } catch (error) {
        console.error("Invalid JWT token:", error);
      }

    // Perform logout logic here, e.g., clear tokens or call a logout API
    fetch("http://localhost/api/petitioner/auth/logout?user="+name, {
      method: "POST",
        headers: {
                      'Content-Type': 'application/json',
                  },
      credentials: "include",
       body: "{}",
    })
      .then((response) => {
        if (response.ok) {
          console.log("Logout successful");
          // Clear local storage or session data if needed
          localStorage.clear();
          sessionStorage.clear();
          // Redirect or update UI
          window.location.href = "/app"; // Redirect to login page
        } else {
          console.error("Logout failed");
        }
      })
      .catch((error) => console.error("Error during logout:", error));
  };

  return (
    <>
      <LogoutButton onClick={() => setShowDialog(true)}>Logout Now</LogoutButton>

      {showDialog && (
        <ConfirmationDialog>
          <p>Are you sure you want to log out?</p>
          <div>
            <DialogButton confirm onClick={handleLogout}>
              Yes, Logout
            </DialogButton>
            <DialogButton onClick={() => setShowDialog(false)}>
              Cancel
            </DialogButton>
          </div>
        </ConfirmationDialog>
      )}
    </>
  );
};

export default Logout;

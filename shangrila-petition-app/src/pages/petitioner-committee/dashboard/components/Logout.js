import React from "react";
import {jwtDecode} from "jwt-decode";

const Logout = () => {
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
    <div onClick={handleLogout} style={{ cursor: "pointer", color: "#e0e0e0" }}>
      Logout now
    </div>
  );
};

export default Logout;

import {jwtDecode} from "jwt-decode";

export const getCurrentUser = () => {
  const getCookie = (cookieName) => {
    const cookies = document.cookie.split("; ");
    for (let cookie of cookies) {
      const [name, value] = cookie.split("=");
      if (name === cookieName) {
        return decodeURIComponent(value);
      }
    }
    return null;
  };

  const accessToken = getCookie("accessToken");
  if (!accessToken) {
    console.error("Access token not found");
    return null;
  }

  try {
    const decoded = jwtDecode(accessToken);
    return decoded.sub; // Assuming `sub` contains the user's email or username
  } catch (error) {
    console.error("Invalid JWT token:", error);
    return null;
  }
};
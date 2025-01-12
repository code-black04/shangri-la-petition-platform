import styled from "styled-components";

export const SlppSigningContainer = styled.header`
  background-color: #000104;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-size: calc(10px + 2vmin);
  color: white;
`;

export const LogoImageContainer = styled.img`
    height: 8vmin;
    pointer-events: none;
    padding-bottom: 20px;
`;

export const FormContainer = styled.form`
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
`;

export const InputRow = styled.div`
    display: flex;
    gap: 5px;
    margin-bottom: 10px;
    &:input {
    width: 100%;
    height: 30px;
  }
`;

export const InputRowGroup = styled.div`
    display: flex;
    flex: 1;
    align-items: center;
    gap: 5px;
`;

export const Input = styled.input`
    width: 100%;
    height: 30px;
    padding: 5px;
    box-sizing: border-box;
`;

export const SigningButtonContainer = styled.div`
  margin: 0 auto;
  padding: 40px;
  border-color: aliceblue;
  width: 300px;
  max-width: 400px;
`;

export const ButtonGroup = styled.div`
  display: flex;
  flex: 1;
  align-items: center;
  gap: 5px;
`;

export const ButtonRow = styled.div`
    display: flex;
    flex: 1;
    align-items: center;
    gap: 5px;
`;

export const Button = styled.button`
    width: 100%;
    height: 30px;
    padding: 5px;
    box-sizing: border-box;
    background-color: black;
    color: white;
    &:hover {
      background-color: white;
      color: black;
      box-sizing: border-box;
    }
`;
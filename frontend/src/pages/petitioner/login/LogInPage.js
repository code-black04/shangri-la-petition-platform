import React, {useState} from "react";
import logo from '../../../logo.svg';
import { SlppSigningContainer, LogoImageContainer, FormContainer, InputRow, InputRowGroup, Input, SigningButtonContainer, ButtonGroup, ButtonRow, Button} from "./LogInStyle.js";
import { useNavigate } from 'react-router-dom';
import PetitionerSignInService from "./PetitionerSignInService.js";

function SignInForm () {
const [emailId, setEmailId] = useState('');
const [password, setPassword] = useState('');
const [showPassword, setShowPassword] = useState(false);

const navigate = useNavigate();

const toggleShowPassword = () => {
    setShowPassword(!showPassword);
}

const handleSignInForm = async (event) => {
    event.preventDefault(); // Prevent the default form submission behavior
    console.log('Form data:', { emailId, password});

    try {
        const username = emailId;
                const userSignInData = {username, password};
        const signInresponse = await PetitionerSignInService.getUser(userSignInData);
        
        if (signInresponse.status === 200) {
            navigate("/app/petitioner-dashboard");
        }
        else {
            alert(await signInresponse.json());
        }
    } catch (error) {
        console.error("Error during login:", error);
        
    }

};

const onPetitionerSignUp = () => {
    navigate("/app/petitioner-signup"); // Navigate to the Sign-Up page
};

const onBackToHomepage = () => {
    navigate("/app/")
}

return (
    <SlppSigningContainer>
    <LogoImageContainer src={logo} alt="logo" />
    <FormContainer onSubmit={handleSignInForm}>

        <InputRow>
            <InputRowGroup>
                <label htmlFor="emailId:"></label>
                <Input type="email" id="emailId" placeholder="Email Id" value={emailId} onChange={(e) => setEmailId(e.target.value)}
                required></Input>
            </InputRowGroup>
        </InputRow>

        <InputRow>
            <InputRowGroup>
                <label htmlFor="password:"></label>
                <Input type={showPassword ? "text" : "password"}
                id="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                ></Input>
            </InputRowGroup>
        </InputRow>

        <InputRow>
            <InputRowGroup>
                    <Input
                    type="checkbox"
                    id="showPassword"
                    style={{width: "20px", display: "flex"}}
                    checked={showPassword}
                    onChange={toggleShowPassword}></Input>
                    <label htmlFor="showPassword" style={{ fontSize: '15px', }}>Show Password</label>
            </InputRowGroup>
        </InputRow>

        <SigningButtonContainer>
            <ButtonRow>
                <ButtonGroup>
                        <Button type="submit">Login</Button>
                        <Button onClick={onBackToHomepage}>Back to Homepage</Button>
                </ButtonGroup>
            </ButtonRow>
        </SigningButtonContainer>

        <InputRow>
          <label style={{ fontSize: '15px', marginTop: '30px' }}>
            Are you a new Petitioner? <a href="#signup" style={{ color: '#007bff', textDecoration: 'underline' }} onClick={onPetitionerSignUp}>Sign Up</a>
          </label>
        </InputRow>

    </FormContainer>
    </SlppSigningContainer>
);
};

export default SignInForm;
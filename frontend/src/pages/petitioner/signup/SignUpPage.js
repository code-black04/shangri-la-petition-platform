import React, {useState, useEffect} from "react";
import logo from '../../../logo.svg';
import { SlppSigningContainer, LogoImageContainer, FormContainer, InputRow, InputRowGroup, Input, SigningButtonContainer, ButtonGroup, ButtonRow, Button, QRScannerContainer, QRScannerArea, QRScannerCancelButton, PageHeader} from "../signup/SignUpStyle.js";
import PetitionerSignUpService from "./PetitionerSignUpService.js";
import { useNavigate } from 'react-router-dom';
import { Html5QrcodeScanner } from "html5-qrcode";
import MessageBanner from "./MessageBanner.js";

function SignUpPage (){

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [emailId, setEmailId] = useState('');
    const [dateOfBirth, setDateOfBirth] = useState('');
    const [password, setPassword] = useState('');
    const [biometricId, setBiometricId] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [isScanning, setIsScanning] = useState(false);

    const [message, setMessage] = useState(''); // To store success or error message
    const [messageType, setMessageType] = useState(''); // 'success' or 'error'


    const navigate = useNavigate();

    const toggleShowPassword = () => {
        setShowPassword(!showPassword);
    }

    const handleSignupForm = async (event) => {
        event.preventDefault();
        
        const userSignUpData = {emailId, firstName, lastName, dateOfBirth, password, biometricId};

        try {
            const newUserResponse = await PetitionerSignUpService.createUser(userSignUpData);
            const responseBody = await newUserResponse.json();
            console.info(responseBody);
            console.info(newUserResponse);

            if (newUserResponse.ok) {
                setMessageType('success');
                setMessage('Signup successful! Redirecting...');
                setTimeout(() => navigate("/app/petitioner"), 2000);
            }
            else {
                setMessageType('error');
                // Parse error response and display detailed messages
                if (responseBody.errorMessageList && responseBody.errorMessageList.length > 0) {
                    setMessage(responseBody.errorMessageList[0].error); // Set only the first error message
                    setTimeout(() => {
                        setMessage('');
                      }, 3000);
                  } else {
                    setMessage(responseBody.responseMessage);
                    setTimeout(() => {
                        setMessage('');
                      }, 3000);
                  }
            }
        } catch (error) {
            setMessageType('error');
            setMessage('An unexpected error occurred. Please try again.');
            console.error('Error adding user: ', error);
        }
    }

    const onBackToHomepage = () => {
        navigate("/app/")
    }

    useEffect(() => {
        if (isScanning) {
            const qrScanner = new Html5QrcodeScanner(
                "qr-reader",
                { fps: 15, qrbox: 250 },
                false
            );

            qrScanner.render(
                (decodedText) => {
                    setBiometricId(decodedText);
                    qrScanner.clear();
                    setIsScanning(false);
                },
                (error) => {
                    console.error("Error scanning QR Code: ", error);
                }
            );

            return () => qrScanner.clear(); // Cleanup on unmount or stop scanning
        }
    }, [isScanning]);
    
    const startQrScanner = () => {
        setIsScanning(true);
    };

    const stopQrScanner = () => {
        setIsScanning(false);
    };

    return (
        <SlppSigningContainer>
        <MessageBanner type={messageType} message={message} />
        <LogoImageContainer src={logo} alt="logo" />

        <PageHeader>Petitioner Registration</PageHeader>

        <FormContainer onSubmit={handleSignupForm}>
            <InputRow>
                <InputRowGroup>
                    <label htmlFor="emailId:"></label>
                    <Input
                    type="email"
                    id="emailId"
                    placeholder="Email Id"
                    value={emailId}
                    onChange={(e) => setEmailId(e.target.value)}
                    required></Input>
                </InputRowGroup>
            </InputRow>
    
            <InputRow>
                <InputRowGroup>
                    <label htmlFor="firstName:"></label>
                    <Input
                    type="text"
                    id="firstName"
                    placeholder="First Name"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    required></Input>
                </InputRowGroup>

                <InputRowGroup>
                    <label htmlFor="lastName:"></label>
                    <Input
                    type="text"
                    id="lastName"
                    placeholder="Last Name"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    required></Input>
                </InputRowGroup>
            </InputRow>
            
    
            <InputRow>
                <InputRowGroup>
                    <label htmlFor="dateOfBirth:"></label>
                    <Input type="text" id="dateOfBirth" placeholder="Date of Birth: YYYY-MM-DD" value={dateOfBirth} onChange={(e) => setDateOfBirth(e.target.value)}
                    required></Input>
                </InputRowGroup>
            </InputRow>
    
            <InputRow>
                <InputRowGroup>
                    <label htmlFor="password:"></label>
                    <Input
                    type={showPassword ? "text" : "password"}
                    id="password"
                    style={{width: "250px", display: "flex"}}
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required></Input>
                </InputRowGroup>

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

            <InputRow>
          <InputRowGroup>
            <Input
              type="text"
              id="biometricId"
              placeholder="Biometric ID"
              value={biometricId}
              onChange={(e) => setBiometricId(e.target.value)}
              required
            ></Input>
            <Button type = "button" onClick={startQrScanner}>Scan</Button>
          </InputRowGroup>
        </InputRow>

        {isScanning && (
          <QRScannerContainer $isVisible={isScanning}>
            <QRScannerArea id="qr-reader" />
            <QRScannerCancelButton type = "button" onClick={stopQrScanner}>
              Cancel Scanning
            </QRScannerCancelButton>
          </QRScannerContainer>
        )}
            
            <SigningButtonContainer>
            <ButtonGroup>
                <ButtonRow>
                    <Button type="submit">Create Account</Button>
                    <Button onClick={onBackToHomepage}>Back to Homepage</Button>
                </ButtonRow>
            </ButtonGroup>
            </SigningButtonContainer>
        </FormContainer>
        </SlppSigningContainer>
    );
}
export default SignUpPage;
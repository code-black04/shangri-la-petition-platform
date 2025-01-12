import React from 'react';
import styled from 'styled-components';

const Banner = styled.div`
  white-space: pre-wrap; /* Preserve line breaks */
  padding: 10px 20px;
  margin: 10px 0;
  border-radius: 5px;
  font-size: 1rem;
  color: ${(props) => (props.type === 'success' ? '#155724' : 
                      props.type === 'error' ? '#721c24' : 
                      props.type === 'warning' ? '#856404' : 
                      '#004085')};
  background-color: ${(props) => (props.type === 'success' ? '#d4edda' : 
                                  props.type === 'error' ? '#f8d7da' : 
                                  props.type === 'warning' ? '#fff3cd' : 
                                  '#cce5ff')};
  border: 1px solid ${(props) => (props.type === 'success' ? '#c3e6cb' : 
                                  props.type === 'error' ? '#f5c6cb' : 
                                  props.type === 'warning' ? '#ffeeba' : 
                                  '#b8daff')};
`;


const MessageBanner = ({ type, message }) => {
  if (!message) return null; // Do not render if no message
  return <Banner type={type}>{message}</Banner>;
};

export default MessageBanner;

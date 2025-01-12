import React from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Welcome from './pages/home/Welcome.js';
import PetitionerLogInPage from './pages/petitioner/login/LogInPage.js';
import PetitionerSignUpPage from './pages/petitioner/signup/SignUpPage.js';
import PetitionerCommitteeLogInPage from './pages/petitioner-committee/login/LogInPage.js';
import Dashboard from './pages/petitioner/dashboard/Dashboard.js';
import PetitionCommitteDashboard from './pages/petitioner-committee/dashboard/Dashboard.js';
import ThresholdManagement from './pages/petitioner-committee/dashboard/components/ThresholdManagement.js';
import PublicPetitionList from './pages/home/PublicPetitionList.js';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/app/" element={<Welcome />} />
          <Route path="/app/open-petition-list" element={<PublicPetitionList/>}/>
          <Route path="/app/petitioner" element={<PetitionerLogInPage />} />
          <Route path="/app/petitioner-committee" element={<PetitionerCommitteeLogInPage />} />
          <Route path="/app/petitioner-signup" element={<PetitionerSignUpPage/>}/>
          <Route path="/app/petitioner-dashboard" element={<Dashboard/>}/>
          <Route path="/app/petition-committee-dashboard" element={<PetitionCommitteDashboard/>}/>
          <Route path="/app/threshold" element={<ThresholdManagement/>}/>
        </Routes>
      </div>
    </Router>
  );
}

export default App;

import React, { useState, useEffect } from "react";
import styled from "styled-components";
import GetAllPetitionService from "../service/GetAllPetitionService.js";
import { Bar } from "react-chartjs-2";
import "chart.js/auto";

const AnalyticsContainer = styled.div`
  padding: 20px;
  background: #1e1e1e;
  color: #e0e0e0;
  max-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const SummaryCards = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(100px, 1fr));
  gap: 10px;
  width: 100%;
  max-width: 800px;
  margin-bottom: 20px;
`;

const Card = styled.div`
  background: #2a2a2a;
  border-radius: 8px;
  padding: 10px 15px;
  text-align: center;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.4);
  transition: transform 0.2s ease, box-shadow 0.2s ease;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 6px 15px rgba(0, 0, 0, 0.6);
  }
`;

const CardTitle = styled.h3`
  font-size: 12px;
  margin-bottom: 5px;
  color: #ffffff;
  text-transform: uppercase;
  letter-spacing: 0.5px;
`;

const CardValue = styled.h2`
  font-size: 20px;
  font-weight: bold;
  color: #007bff;
`;

const ChartContainer = styled.div`
  width: 100%;
  max-width: 800px;
  padding: 15px;
  background: #2a2a2a;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.4);
  margin-top: 20px;
`;

const ChartTitle = styled.h3`
  text-align: center;
  margin-bottom: 15px;
  font-size: 16px;
  font-weight: bold;
  color: #ffffff;
`;

const Analytics = () => {
  const [petitions, setPetitions] = useState([]);
  const [openCount, setOpenCount] = useState(0);
  const [closedCount, setClosedCount] = useState(0);
  const [totalSignatures, setTotalSignatures] = useState(0);

  useEffect(() => {
    const fetchAnalyticsData = async () => {
      try {
        const response = await GetAllPetitionService.getAllPetitions("All");
        if (!response.ok) {
          throw new Error("Failed to fetch petitions");
        }
        const data = await response.json();
        setPetitions(data);

        // Analytics calculations
        const open = data.filter((petition) => petition.status === "Open").length;
        const closed = data.filter((petition) => petition.status === "Closed").length;
        const totalSignatures = data.reduce((sum, petition) => sum + petition.signature, 0);

        setOpenCount(open);
        setClosedCount(closed);
        setTotalSignatures(totalSignatures);
      } catch (err) {
        console.error(err.message);
      }
    };

    fetchAnalyticsData();
  }, []);

  // Data for charts
  const barData = {
    labels: ["Open", "Closed"],
    datasets: [
      {
        label: "Number of Petitions",
        data: [openCount, closedCount],
        backgroundColor: ["#007bff", "#ff6b6b"],
        borderWidth: 2,
        borderColor: ["#0056b3", "#cc0000"],
        hoverBackgroundColor: ["#0056b3", "#cc0000"],
      },
    ],
  };

  const barOptions = {
    plugins: {
      legend: {
        display: true,
        labels: {
          color: "#e0e0e0",
          font: {
            size: 12,
          },
        },
      },
      tooltip: {
        callbacks: {
          label: (context) => `Petitions: ${context.raw}`,
        },
      },
    },
    scales: {
      x: {
        ticks: {
          color: "#e0e0e0",
        },
        grid: {
          display: false,
        },
      },
      y: {
        ticks: {
          color: "#e0e0e0",
        },
        grid: {
          color: "#444",
        },
      },
    },
  };

  return (
    <AnalyticsContainer>
      <SummaryCards>
        <Card>
          <CardTitle>Total Petitions</CardTitle>
          <CardValue>{petitions.length}</CardValue>
        </Card>
        <Card>
          <CardTitle>Open Petitions</CardTitle>
          <CardValue>{openCount}</CardValue>
        </Card>
        <Card>
          <CardTitle>Closed Petitions</CardTitle>
          <CardValue>{closedCount}</CardValue>
        </Card>
        <Card>
          <CardTitle>Average Signatures</CardTitle>
          <CardValue>
            {petitions.length > 0 ? (totalSignatures / petitions.length).toFixed(2) : 0}
          </CardValue>
        </Card>
      </SummaryCards>

      <ChartContainer>
        <ChartTitle>Petition Status Distribution</ChartTitle>
        <Bar data={barData} options={barOptions} />
      </ChartContainer>
    </AnalyticsContainer>
  );
};

export default Analytics;

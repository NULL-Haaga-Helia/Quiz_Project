import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { getQuizById, getAllQuizReviews } from "../services/api";
import { useLocation } from "react-router-dom";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import { Box } from "@mui/material";



function QuizReviewList() {

  const [quiz, setQuiz] = useState(null);
  const location = useLocation();
  const { quizId } = location.state;
  const [ReviewsList, setReviewsList] = useState([]);
  const navigate = useNavigate();


  useEffect(() => {
    if (quizId) {
      fetchQuiz();
      fetchReviews();
    }
  }, [quizId]);

  const fetchQuiz = () => {
    getQuizById(quizId)
      .then((responseData) => setQuiz(responseData))
      .catch((err) => {
        console.error("Failed to fetch quiz:", err);
        setQuiz(null);
      });
  };

  const fetchReviews = () => {
    getAllQuizReviews()
      .then((responseData) => {
        console.log("Fetched reviews:", responseData);
        setReviewsList(responseData);
      })
      .catch((err) => console.error("Error fetching reviews", err));
  };

  return (
    <Box sx={{ width: "100%", marginTop: 8 }}>
      <Typography
        variant="h4"
        gutterBottom
        sx={{ textAlign: "left", marginBottom: 2 }}
      >
        Results of ""
      </Typography>

      <TableCell
      //		component="th"
      //		scope="row"
      //		style={{ cursor: "pointer", color: "#1976d2" }}
      //		onClick={() => handleQuizReviewsClick(quiz.id)}
      //		align="left"
      >
        Write your own review
      </TableCell>

      {ReviewsList && ReviewsList.length > 0 ? (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell sx={{ fontWeight: "bold" }} align="left">
                  Nickname
                </TableCell>
                <TableCell sx={{ fontWeight: "bold" }} align="left">
                  rating
                </TableCell>
                <TableCell sx={{ fontWeight: "bold" }} align="left">
                  review
                </TableCell>
              </TableRow>
            </TableHead>
          </Table>
        </TableContainer>
      ) : (
        <Typography variant="h6" sx={{ textAlign: "center" }}>
          No Reviews found
        </Typography>
      )}
    </Box>
  );
}

export default QuizReviewList;
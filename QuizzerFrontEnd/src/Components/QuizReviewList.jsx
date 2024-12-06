import { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { getQuizById, getAllQuizReviews } from "../services/api"; 
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
  // States
  const [quiz, setQuiz] = useState(null);
  const [reviewsList, setReviewsList] = useState([]);
  const location = useLocation();
  const { quizId } = location.state; 
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
    getAllQuizReviews(quizId) 
      .then((responseData) => {
        console.log("Fetched reviews:", responseData);
        setReviewsList(responseData); 
      })
      .catch((err) => console.error("Error fetching reviews:", err));
  };

  const handleWriteReviewClick = () => {
    console.log("Navigating to review submission page");
    navigate("/submitreview", { state: { quizId: quiz.id } });
  };

  return (
    <Box sx={{ width: "100%", marginTop: 8 }}>
      <Typography
        variant="h4"
        gutterBottom
        sx={{ textAlign: "left", marginBottom: 2 }}
      >
        Results of {quiz?.name || "Quiz"}
      </Typography>

      {/* Button to navigate to review submission */}
      <TableCell
        sx={{
          cursor: "pointer",
          color: "#1976d2",
          fontWeight: "bold",
        }}
        onClick={handleWriteReviewClick}
        align="left"
      >
        Write your own review
      </TableCell>

      {/* Display Reviews Table if reviews exist */}
      {reviewsList && reviewsList.length > 0 ? (
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell sx={{ fontWeight: "bold" }} align="left">
                Nickname
              </TableCell>
              <TableCell sx={{ fontWeight: "bold" }} align="left">
                Rating
              </TableCell>
              <TableCell sx={{ fontWeight: "bold" }} align="left">
                Review
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {reviewsList.map((review) => (
              <TableRow
                key={review.id}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell component="th" scope="row">
                  {review.nickname}
                </TableCell>
                <TableCell align="left">{review.rating}</TableCell>
                <TableCell align="left">{review.review}</TableCell>
              </TableRow>
            ))}
          </TableBody>
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

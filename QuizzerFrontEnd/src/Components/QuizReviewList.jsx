import { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import {
  getQuizById,
  getAllQuizReviews,
  deleteReview,
  editReview,
  addReview,
} from "../services/api";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import { Box, Modal, TextField, Button } from "@mui/material";

function QuizReviewList() {
  const [quiz, setQuiz] = useState(null);
  const [reviewsList, setReviewsList] = useState([]);
  const [editModalOpen, setEditModalOpen] = useState(false);
  const [addReviewModalOpen, setAddReviewModalOpen] = useState(false);

  const [currentReview, setCurrentReview] = useState(null);

  const [updatedNickname, setUpdatedNickname] = useState("");
  const [updatedRating, setUpdatedRating] = useState("");
  const [updatedReview, setUpdatedReview] = useState("");
  const [updatedWrittenOn, setUpdatedWrittenOn] = useState("");

  const [newRating, setNewRating] = useState("");
  const [newReview, setNewReview] = useState("");
  const [newNickname, setNewNickname] = useState("");
  const [newWrittenOn, setNewWrittenOn] = useState("");

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

  const handleReviewDelete = (reviewId) => {
    if (window.confirm("Are you sure you want to delete this review?")) {
      console.log("reviewId:", reviewId);
      deleteReview(reviewId)
        .then(() => {
          fetchReviews();
        })
        .catch((err) => console.error("Error deleting review:", err));
    }
  };

  const handleReviewEdit = (review) => {
    setCurrentReview(review);
    setUpdatedNickname(review.nickname);
    setUpdatedRating(review.rating);
    setUpdatedReview(review.review);
    setUpdatedWrittenOn(review.writtenOn);
    setEditModalOpen(true);
  };

  const handleSaveEdit = () => {
    const updatedData = {
      nickname: updatedNickname,
      rating: updatedRating,
      review: updatedReview,
      writtenOn: updatedWrittenOn,
    };

    editReview(quizId, currentReview.reviewId, updatedData)
      .then((updatedReview) => {
        console.log("Review edited successfully:", updatedReview);
        setEditModalOpen(false);
        fetchReviews();
      })
      .catch((err) => console.error("Error editing review:", err));
  };

  const handleWriteReviewClick = () => {
    setAddReviewModalOpen(true);
  };

  const handleSaveNewReview = () => {
    const newReviewData = {
      nickname: newNickname,
      rating: newRating,
      review: newReview,
      writtenOn: newWrittenOn,
    };

    addReview(quizId, newReviewData)
      .then((addedReview) => {
        console.log("Review added successfully:", addedReview);
        setAddReviewModalOpen(false);
        fetchReviews();
      })
      .catch((err) => console.error("Error adding review:", err));
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
                <TableCell sx={{ fontWeight: "bold" }} align="left">
                  Written On
                </TableCell>
                <TableCell sx={{ fontWeight: "bold" }} align="left">
                  Edit
                </TableCell>
                <TableCell sx={{ fontWeight: "bold" }} align="left">
                  Delete
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
                  <TableCell align="left"> {review.writtenOn}</TableCell>
                  <TableCell align="left">
                    <Typography
                      component="span"
                      style={{ cursor: "pointer", color: "#1976d2" }}
                      onClick={() => handleReviewEdit(review)}
                    >
                      Edit
                    </Typography>
                  </TableCell>
                  <TableCell align="left">
                    <Typography
                      component="span"
                      style={{
                        cursor: "pointer",
                        color: "#1976d2",
                        marginRight: 8,
                      }}
                      onClick={() =>
                        handleReviewDelete(review.reviewId)
                      }
                    >
                      Delete
                    </Typography>
                  </TableCell>
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

      <Modal
        open={addReviewModalOpen}
        onClose={() => setAddReviewModalOpen(false)}
      >
        <Box
          sx={{
            padding: 4,
            backgroundColor: "white",
            margin: "auto",
            marginTop: 10,
            width: 400,
          }}
        >
          <Typography variant="h6" gutterBottom>
            Write a Review
          </Typography>
          <TextField
            fullWidth
            label="Nickname"
            value={newNickname}
            onChange={(e) => setNewNickname(e.target.value)}
            sx={{ marginBottom: 2 }}
          />
          <TextField
            fullWidth
            label="Rating"
            type="number"
            value={newRating}
            onChange={(e) => setNewRating(e.target.value)}
            sx={{ marginBottom: 2 }}
          />
          <TextField
            fullWidth
            label="Review"
            multiline
            rows={4}
            value={newReview}
            onChange={(e) => setNewReview(e.target.value)}
            sx={{ marginBottom: 2 }}
          />
          <TextField
            fullWidth
            label="Written On"
            value={newWrittenOn}
            onChange={(e) => setNewWrittenOn(e.target.value)}
            sx={{ marginBottom: 2 }}
          />
          <Button
            variant="contained"
            color="primary"
            onClick={handleSaveNewReview}
          >
            Save Review
          </Button>
        </Box>
      </Modal>

      <Modal open={editModalOpen} onClose={() => setEditModalOpen(false)}>
        <Box
          sx={{
            padding: 4,
            backgroundColor: "white",
            margin: "auto",
            marginTop: 10,
            width: 400,
          }}
        >
          <Typography variant="h6" gutterBottom>
            Edit Review
          </Typography>

          <TextField
            fullWidth
            label="Rating"
            type="number"
            value={updatedRating}
            onChange={(e) => setUpdatedRating(e.target.value)}
            sx={{ marginBottom: 2 }}
          />
          <TextField
            fullWidth
            label="Review"
            multiline
            rows={4}
            value={updatedReview}
            onChange={(e) => setUpdatedReview(e.target.value)}
            sx={{ marginBottom: 2 }}
          />
          <TextField
            fullWidth
            label="Written On"
            value={updatedWrittenOn}
            onChange={(e) => setUpdatedWrittenOn(e.target.value)}
            sx={{ marginBottom: 2 }}
          />

          <Button variant="contained" color="primary" onClick={handleSaveEdit}>
            Save
          </Button>
        </Box>
      </Modal>
    </Box>
  );
}

export default QuizReviewList;

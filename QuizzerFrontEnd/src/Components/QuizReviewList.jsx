import { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { getQuizById, getAllQuizReviews, deleteReview, editReview, addReview } from "../services/api";
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
  const [updatedRating, setUpdatedRating] = useState("");
  const [updatedReview, setUpdatedReview] = useState(""); // Review comment for editing
  const [newRating, setNewRating] = useState(""); // For new review
  const [newReview, setNewReview] = useState(""); // For new review

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

  /*
  const handleReviewDelete = (quizId, reviewId) => {
    if (window.confirm("Are you sure you want to delete this review?")) {
      fetch(`/api/deletereview/${reviewId}`, {
        method: "DELETE",
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Error deleting review");
          }
          setReviewsList((prevReviews) => prevReviews.filter((review) => review.id !== reviewId));
        })
        .catch((err) => console.error("Error deleting review:", err));
    }
  };
  */
  const handleReviewDelete = (quizId, reviewId) => {
    if (window.confirm("Are you sure you want to delete this review?")) {
      deleteReview(quizId, reviewId)
        .then((response) => {
          if (!response.ok) {
            throw new Error("Error deleting review");
          }
          setReviewsList((prevReviews) => prevReviews.filter((review) => review.id !== reviewId));
        })
        .catch((err) => console.error("Error deleting review:", err));
    }
  };


  const handleReviewEdit = (review) => {
    setCurrentReview(review);
    setUpdatedRating(review.rating);
    setUpdatedReview(review.review);
    setEditModalOpen(true);
  };

  const handleSaveEdit = () => {
    const updatedData = {
      rating: updatedRating,
      review: updatedReview
    };

    editReview(quizId, currentReview.id, updatedData)
      .then((updatedReview) => {
        console.log("Review edited successfully:", updatedReview);
        setReviewsList((prev) =>
          prev.map((review) =>
            review.id === currentReview.id ? { ...review, ...updatedReview } : review
          )
        );
        setEditModalOpen(false);
      })
      .catch((err) => console.error("Error editing review:", err));
  };

  const handleWriteReviewClick = () => {
    setAddReviewModalOpen(true);
  };

  const handleSaveNewReview = () => {
    const newReviewData = {
      rating: newRating,
      review: newReview,
    };

    addReview(quizId, newReviewData)
      .then((addedReview) => {
        console.log("Review added successfully:", addedReview);
        setReviewsList((prev) => [...prev, addedReview]);
        setAddReviewModalOpen(false);
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
                <TableCell sx={{ fontWeight: "bold" }} align="left">Nickname</TableCell>
                <TableCell sx={{ fontWeight: "bold" }} align="left">Rating</TableCell>
                <TableCell sx={{ fontWeight: "bold" }} align="left">Review</TableCell>
                <TableCell sx={{ fontWeight: "bold" }} align="left">Actions</TableCell>
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
                  <TableCell align="left">
                    <Typography
                      component="span"
                      style={{
                        cursor: "pointer",
                        color: "#1976d2",
                        marginRight: 8,
                      }}
                      onClick={() => handleReviewDelete(review.id)}
                    >
                      Delete
                    </Typography>
                    <Typography
                      component="span"
                      style={{ cursor: "pointer", color: "#1976d2" }}
                      onClick={() => handleReviewEdit(review)}
                    >
                      Edit
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

      <Modal open={addReviewModalOpen} onClose={() => setAddReviewModalOpen(false)}>
        <Box sx={{ padding: 4, backgroundColor: "white", margin: "auto", marginTop: 10, width: 400 }}>
          <Typography variant="h6" gutterBottom>
            Write a Review
          </Typography>
          <TextField
            fullWidth
            label="Rating"
            type="number"
            value={newRating}
            onChange={(e) => setNewRating(e.target.value)}
            sx={{ marginBottom: 2 }}
          />
          <Button variant="contained" color="primary" onClick={handleSaveNewReview}>
            Save Review
          </Button>
        </Box>
      </Modal>

      <Modal open={editModalOpen} onClose={() => setEditModalOpen(false)}>
        <Box sx={{ padding: 4, backgroundColor: "white", margin: "auto", marginTop: 10, width: 400 }}>
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
          <Button variant="contained" color="primary" onClick={handleSaveEdit}>
            Save
          </Button>
        </Box>
      </Modal>
    </Box>
  );
}

export default QuizReviewList;

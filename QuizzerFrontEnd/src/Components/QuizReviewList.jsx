import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import {
  getQuizById,
  getAllQuizReviews,
  deleteReview,
  editReview,
  addReview,
} from "../services/api";
import Typography from "@mui/material/Typography";
import { Box, Modal, TextField, Button, Link } from "@mui/material";

function QuizReviewList() {
  //States:
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


  //Functions:
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

  //Deleting reviews:
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

  //Editing reviews
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

  //Adding new review
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

      <Typography
        variant="body1"
        color="textSecondary"
        gutterBottom
        sx={{ marginBottom: 4 }}
      >
        {reviewsList 
          ? `${(
              reviewsList.reduce((sum, review) => sum + parseFloat(review.rating), 0) /
              reviewsList.length
            ).toFixed(1)}/5 rating average based on ${reviewsList.length} reviews`
          : "No reviews yet"}
      </Typography>

      <Link
  onClick={handleWriteReviewClick}
  sx={{
    display: "inline-block",
    fontWeight: "bold",  
    cursor: "pointer",
    marginBottom: "20px"
  }}
>
  Write your own review
</Link>

      {/* Review List */}
      {reviewsList && reviewsList.length > 0 ? (
        <Box>
          {reviewsList.map((review) => (
            <Box
              key={review.reviewId}
              sx={{
                border: "1px solid #e0e0e0",
                padding: 2,
                marginBottom: 2,
                borderRadius: 2,
              }}
            >
              <Typography variant="h6" sx={{ fontWeight: "bold" }}>
                {review.nickname}
              </Typography>
              <Typography variant="body2" color="textSecondary">
                Rating: {review.rating}/5
              </Typography>
              <Typography variant="body1">{review.review}</Typography>
              <Typography variant="body2" color="textSecondary">
                Written On: {review.writtenOn}
              </Typography>

              <Box sx={{ marginTop: 2 }}>
                <Typography
                  component="span"
                  sx={{
                    cursor: "pointer",
                    color: "#1976d2",
                    marginRight: 2,
                  }}
                  onClick={() => handleReviewEdit(review)}
                >
                  Edit
                </Typography>
                <Typography
                  component="span"
                  sx={{
                    cursor: "pointer",
                    color: "#1976d2",
                  }}
                  onClick={() => handleReviewDelete(review.reviewId)}
                >
                  Delete
                </Typography>
              </Box>
            </Box>
          ))}
        </Box>
      ) : (
        <Typography variant="h6" sx={{ textAlign: "center" }}>
          No Reviews found
        </Typography>
      )}

      {/* Add Review Modal */}
      <Modal open={addReviewModalOpen} onClose={() => setAddReviewModalOpen(false)}>
        <Box sx={{ padding: 4, backgroundColor: "white", margin: "auto", marginTop: 10, width: 400 }}>
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
          <Button variant="contained" color="primary" onClick={handleSaveNewReview}>
            Save Review
          </Button>
        </Box>
      </Modal>

      {/* Edit Review Modal */}
      <Modal open={editModalOpen} onClose={() => setEditModalOpen(false)}>
        <Box sx={{ padding: 4, backgroundColor: "white", margin: "auto", marginTop: 10, width: 400 }}>
          <Typography variant="h6" gutterBottom>
            Edit Review
          </Typography>
          <TextField
            fullWidth
            label="Nickname"
            value={updatedNickname}
            onChange={(e) => setUpdatedNickname(e.target.value)}
            sx={{ marginBottom: 2 }}
          />
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
            Save Changes
          </Button>
        </Box>
      </Modal>
    </Box>
  );
}

export default QuizReviewList;

import { useState } from "react";
import {Button,Dialog,DialogTitle,DialogContent,DialogActions,TextField,} from "@mui/material";
import axios from "axios";



export default function Addquestion({ onQuestionAdded}){

    const[open, setOpen] = useState(false);

    const[question, setQuestion] = useState({
        questionText: '',
        difficulty: '',
        quizId: quizId,
    });

    const handleClickOpne = () => {
        setOpen(true);
    }

    const handleClose = () => {
        setOpen(false);

    }

    const handleInputChange = (e) => {
        setQuestion({...question, [e.target.name]: e.target.value})
    }    

    const addQuestion = () => {
        props.saveQuestion(question);
        handleClose();

    }

    const saveQuestion = async () => {
        try{
            const response = await axios.post("http://localhost:8080/addquestion", question);
            console.log("Question save:", response.data);
            onQuestionAdded(response.data);
            handleClose();
        } catch(error){
            console.log("Error saving question:", error);
            
        }
    };

    return (
        <div>
          <Button variant="contained" color="primary" onClick={handleClickOpen}>
            Add Question
          </Button>
    
          <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">New Question</DialogTitle>
            <DialogContent>
              <TextField
                autoFocus
                margin="dense"
                name="questionText"
                value={question.questionText}
                onChange={handleInputChange}
                label="Question Text"
                fullWidth
              />
              <TextField
                margin="dense"
                name="difficulty"
                value={question.difficulty}
                onChange={handleInputChange}
                label="Difficulty"
                select
                fullWidth
              >
                <MenuItem value="Easy">Easy</MenuItem>
                <MenuItem value="Medium">Medium</MenuItem>
                <MenuItem value="Hard">Hard</MenuItem>
              </TextField>
            </DialogContent>
            <DialogActions>
              <Button onClick={handleClose} color="secondary">
                Cancel
              </Button>
              <Button onClick={saveQuestion} color="primary">
                Save
              </Button>
            </DialogActions>
          </Dialog>
        </div>
      );
    }
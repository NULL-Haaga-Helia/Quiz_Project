import { useState } from "react";
import { Button, Dialog, DialogTitle, DialogContent, DialogActions, TextField } from "@mui/material";



export default function EditQuiz({quiz, updateQuiz}){

const[open, setOpen] = useState(false);
const[editedQuiz, setEditQuiz] = useState({ name: '', description: '', published: '', addedOn: ''});


const handleClickOpen = () => {

    setEditQuiz({...quiz});
    setOpen(true);

}

const handleClose = () => {
    setOpen(false);
}

    
const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditQuiz((prevState) => ({...prevState, [name]:value, }));
};


const saveQuiz = () => {
    updateQuiz(editedQuiz, quiz._links.self.href);
    handleClose(); 
}

 

    return(
        <div>
            <Button variant="outlined" color="primary" onClick={handleClickOpen}>
            Edit Quiz       
            </Button>

           <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">Edit Customer</DialogTitle>
           
           
           
           
           
           </Dialog>

        </div>


    );

}
import { Button } from "@material-tailwind/react";
import React from 'react'
import { Link } from 'react-router-dom'

const CreateBookButton = () => {
    return (
        <Link to='/create'>
            <Button type='primary'>
                Add New
            </Button>
        </Link>
    )
}

export default CreateBookButton
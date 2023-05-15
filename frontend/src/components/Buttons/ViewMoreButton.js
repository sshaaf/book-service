import { Button } from "@material-tailwind/react";
import React from 'react'
import { Link } from 'react-router-dom'

const ViewMoreButton = ({book}) => {
    return (
        <Link to={`/${book.isbn}`}>
            <Button type='primary'>
                View More
            </Button>
        </Link>
    )
}

export default ViewMoreButton
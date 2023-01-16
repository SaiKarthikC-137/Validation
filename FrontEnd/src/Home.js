import React from 'react'
import { useNavigate } from 'react-router-dom';
import './Home.css'

export default function Home() {
    const navigate=useNavigate();
    const handleLogout=(e)=>{
        navigate('/login');
    }
    return (
    <div className='homepage'>
        <h1>Home</h1>
        <div className='d-grid gap-2 mt-3'>
        <button className='btn btn-secondary' onClick={handleLogout}>
            Logout
        </button>
        </div>
    </div>
    )
  }

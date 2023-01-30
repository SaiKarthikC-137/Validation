import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './Home.css'

export default function Home() {
    const [user,setUser]=useState({
        email:"",
        password:"",
        confirmPassword:"",
        mobile:"",
        username:"",
        address:"",
        role:"user",
        activated:false
    });
    const navigate=useNavigate();
    useEffect(() => {
        console.log(localStorage.getItem("user"));
    axios.get('http://localhost:8080/getuserbyemail',{params:{email:JSON.parse(localStorage.getItem("user"))['email']}})
      .then(res => {
        console.log(res.data);
        setUser(res.data);
      })
      .catch(err => {
        console.error(err);
      });
  }, []);
  const onInputChange = e => {
        const { name, value } = e.target;
        setUser(prev => ({
            ...prev,
            [name]: value
        }));
    }
    const handleUpdate=(e)=>{
        axios.post('http://localhost:8080/updateuser',user).then(res=> {
            console.log(res);
        })
    }
    const handleLogout=(e)=>{
        localStorage.removeItem("user");
        navigate('/login');
    }
    return (
    <div className="homepage">
      <form>
        <div>
        <h3>Home</h3>
        <div className="form-group mt-3">
          <label>User name</label>
          <input
            type="text"
            name="username"
            className="form-control mt-1"
            value={user['username']}
            onChange={onInputChange}
          />
        </div>
        <div className="form-group mt-3">
          <label>Mobile</label>
          <input
            type="text"
            name="mobile"
            className="form-control mt-1"
            value={user['mobile']}
            onChange={onInputChange}
          />
        </div>
        <div className="form-group mt-3">
          <label>Address</label>
          <input
            type="text"
            name="address"
            className="form-control mt-1"
            value={user['address']}
            onChange={onInputChange}
          />
        </div>
        <div className="d-grid gap-2 mt-3">
          <button type="submit" className="btn btn-primary" onClick={handleUpdate}>
            Update details
          </button>
        </div>
        <div className="d-grid gap-2 mt-3">
          <button className="btn btn-secondary" onClick={handleLogout}>
            Logout
          </button>
        </div>
        </div>
      </form>

        </div>
    )
  }

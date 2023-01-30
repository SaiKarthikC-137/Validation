import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Dashboard() {
  const [users, setUsers] = useState([]);
  const [del,setDel] = useState(0);
  useEffect(() => {
    axios.get('http://localhost:8080/findall')
      .then(res => {
        setUsers(res.data);
      })
      .catch(err => {
        console.error(err);
      });
  }, [del]);
  const handleActivation=(username)=>{
    axios.get('http://localhost:8080/activate',{params:{"username":username}})
    .then(res=> {
      console.log(res.data);
      var button=document.getElementById('activationbtn');
      button.style.visibility="hidden";
    })
  }
  const handleDelete=(email)=> {
    axios.get('http://localhost:8080/delete',{params:{"email":email}}).then(res=>{
      console.log(res.data);
    })
    setDel((del)=>del+1);
    console.log(del);
  }
  const navigate=useNavigate();
    const handleLogout=(e)=>{
      localStorage.removeItem("user");
      navigate('/login');
    }
  return (
    <div className="container-fluid">
      <h1>Dashboard</h1>
      <div className="row">
        <div className="col-md-8 offset-md-2">
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map(user => (
            <tr key={user.username}>
              <td>{user.username}</td>
              <td>{user.email}</td>
              <td>{user.role}</td>
              <td>
                    {user.activated == false ? (
                      <button id='activationbtn' className="btn btn-success btn-sm" onClick={()=>{handleActivation(user.username)}}>Activate</button>
                    ) : <button className="btn btn-danger btn-sm" onClick={()=>{handleDelete(user.email)}}>Delete</button>}
                  </td>
            </tr>
          ))}
        </tbody>
      </table>
      </div>
      </div>
      <div className='d-grid gap-2 mt-3'>
        <button className='btn btn-secondary' onClick={handleLogout}>
            Logout
        </button>
        </div>
    </div>
  );
}

export default Dashboard;
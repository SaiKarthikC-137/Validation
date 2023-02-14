import axios from 'axios';
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import hidePwdImg from './res/hide-password.svg'
import showPwdImg from './res/show-password.svg'
import './Login.css';

export default function Login() {
    const [input,setInput]=useState({
      email:"",
      password:""
    });
    const [error,setError]=useState({
      email:"",
      password:""
    })
    const [isRevealPwd,setIsRevealPwd]=useState(false);
    const [status,setStatus] =useState("");
    const navigate=useNavigate();
    const onInputChange = e => {
        const { name, value } = e.target;
        setInput(prev => ({
            ...prev,
            [name]: value
        }));
        validateInput(e);
    }
    const validateInput= e => {
      let { name, value } = e.target;
    setError(prev => {
      const stateObj = { ...prev, [name]: "" };
 
      switch (name) {
        case "email":
          if (!value) {
            stateObj[name] = "Please enter email";
          }
          else if(!validateEmail(value)) {
            stateObj[name] = "Invalid email"
          }
          break;
        case "password":
          if (!value) {
            stateObj[name] = "Please enter password.";
          }
          break;
        default:
          break;
      }
 
      return stateObj;
    });
    }
    const validateEmail=(email)=> {
        const emreg=/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
        if(!emreg.test(email)) {
            return false;
        }
        return true;
    }
    const handleSubmit=(e)=> {
        e.preventDefault();
        if(!validateEmail) {
          return;
        }
        axios.post('http://localhost:8080/login',{"email":input['email'],"password":input['password']})
        .then(res=> {
            if(res.data) {
              localStorage.setItem("user",JSON.stringify(input))
              axios.get('http://localhost:8080/getRole',{params: {email:input['email']}}).then(res=> {
                if(res.data==="admin")
                  navigate("/dashboard");
                else
                  navigate("/home");
              }).catch(err=>{
                console.log(err);
              });
            }
            else {
                setStatus("Login Failed");
            }
        }).catch(err=>{
          console.log(err);
        });
    }
    const handleRegister=(e)=> {
        navigate("/register");
    }
    return (
      <div className="Auth-form-container">
      <form className="Auth-form">
        <div className="Auth-form-content">
        <h3 className="Auth-form-title">Login</h3>
        <div className="form-group mt-3">
          <label>Email address</label>
          <input
            type="email"
            name="email"
            className="form-control mt-1"
            placeholder="Enter email"
            onChange={onInputChange}
            onBlur={validateInput}
          />
          {error.email && <span className='err'>{error.email}</span>}
        </div>
        <div className="form-group mt-3">
          <label>Password</label>
          <input
            type={isRevealPwd?"text":"password"}
            name="password"
            className="form-control mt-1"
            placeholder="Enter password"
            onChange={onInputChange}
            onBlur={validateInput}
          />
          <img
          title={isRevealPwd ? "Hide password" : "Show password"}
          width="20px"
          height="20px"
          src={isRevealPwd ? hidePwdImg : showPwdImg}
          alt=""
          onClick={() => setIsRevealPwd(prevState => !prevState)}
        />
        <span>Show password</span>
          {error.password && <span className='err'>{error.password}</span>}
        </div>
        <div>
          {status}
        </div>
        <div className="d-grid gap-2 mt-3">
          <button type="submit" className="btn btn-primary" onClick={handleSubmit}>
            Submit
          </button>
        </div>
        <div className="d-grid gap-2 mt-3">
            <button className="btn btn-secondary" onClick={handleRegister}>
                Create user
            </button>
        </div>
      </div>
      </form>
      </div>
    )
  }
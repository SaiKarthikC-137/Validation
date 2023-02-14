import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import hidePwdImg from './res/hide-password.svg'
import showPwdImg from './res/show-password.svg'
import './Register.css';

export default function Register() {
    const [input,setInput]=useState({
        email:"",
        password:"",
        confirmPassword:"",
        mobile:"",
        username:"",
        address:"",
        role:"user",
        activated:false
    })
    const [error,setError]=useState({
        email:"",
        password:"",
        confirmPassword:""
    })
    const [isRevealPwd,setIsRevealPwd]=useState(false);
    const [status,setStatus] = useState("")
    const navigate=useNavigate();
    const onInputChange = e => {
        const { name, value } = e.target;
        setInput(prev => ({
            ...prev,
            [name]: value
        }));
        console.log(input);
        validateInput(e);
    }
    const validateInput = e => {
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
          } else if(!validatePassword(value)) {
            stateObj[name] = status;
          } else if (input.confirmPassword && value !== input.confirmPassword) {
            stateObj["confirmPassword"] = "Passwords do not match.";
          } else {
            stateObj["confirmPassword"] = input.confirmPassword ? "" : error.confirmPassword;
          }
          break;
 
        case "confirmPassword":
          if (!value) {
            stateObj[name] = "Please confirm password.";
          } else if (input.password && value !== input.password) {
            stateObj[name] = "Passwords do not match.";
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
        if(!emreg.test(input['email'])) {
            setStatus("Invalid Email");
            return false;
        }
        return true;
    }
    const validatePassword=(pass)=> {
        const passwordLength =      pass.length;
        const uppercasePassword =   /(?=.*?[A-Z])/.test(pass);
        const lowercasePassword =   /(?=.*?[a-z])/.test(pass);
        const digitsPassword =      /(?=.*?[0-9])/.test(pass);
        const specialCharPassword = /(?=.*?[#?!@$%^&*-])/.test(pass);
        const minLengthPassword =   /.{8,}/.test(pass);
        let errMsg ="";
        if(passwordLength===0){
            errMsg="Password is empty";
        }else if(!uppercasePassword){
            errMsg="Password must contain at least one uppercase";
        }else if(!lowercasePassword){
            errMsg="Password must contain at least one lowercase";
        }else if(!digitsPassword){
            errMsg="Password must contain at least one digit";
        }else if(!specialCharPassword){
            errMsg="Password must contain at least one special characters";
        }else if(!minLengthPassword){
            errMsg="Password must contain minumum 8 characters";
        }else{
            errMsg="";
        }
        setStatus(errMsg);
        if(errMsg==="") return true;
        return false;
    }
    const handleSubmit=(e)=> {
        e.preventDefault();
        if(!validateEmail(input['email'])) {
            return;
        }
        if(!validatePassword(input['password'])) {
            return;
        }
        axios.post('http://localhost:8080/register',input).then(res=>{
          navigate('/login')
        }).catch(err=>{
          setStatus(err.response.data)
          console.log(status);
        })
    }
    const handleLogin=(e)=>{
        navigate('/login');
    }
    return (
        <div className="Auth-form-container">
      <form className="Auth-form">
        <div className="Auth-form-content">
        <h3 className="Auth-form-title">Register</h3>
        <div className="form-group mt-3">
          <label>User name</label>
          <input
            type="text"
            name="username"
            className="form-control mt-1"
            placeholder="Enter username"
            onChange={onInputChange}
          />
        </div>
        <div className="form-group mt-3">
          <label>Mobile</label>
          <input
            type="text"
            name="mobile"
            className="form-control mt-1"
            placeholder="Enter mobile"
            onChange={onInputChange}
          />
        </div>
        <div className="form-group mt-3">
          <label>Address</label>
          <input
            type="text"
            name="address"
            className="form-control mt-1"
            placeholder="Enter address"
            onChange={onInputChange}
          />
        </div>
        <div className="form-group mt-3">
          <label>Email address</label>
          <input
            type="email"
            name="email"
            className="form-control mt-1"
            placeholder="Enter email"
            onBlur={validateInput}
            onChange={onInputChange}
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
            onBlur={validateInput}
            onChange={onInputChange}
          />
          {error.password && <span className='err'>{error.password}</span>}
        </div>
        <div className="form-group mt-3">
          <label>Confirm Password</label>
          <input
            type={isRevealPwd?"text":"password"}
            name="confirmPassword"
            className="form-control mt-1"
            placeholder="Confirm password"
            onBlur={validateInput}
            onChange={onInputChange}
          />
          <img
          title={isRevealPwd ? "Hide password" : "Show password"}
          width="20px"
          height="20px"
          src={isRevealPwd ? hidePwdImg : showPwdImg}
          alt=""
          onClick={() => setIsRevealPwd(prevState => !prevState)}
        />
        <span>Show password</span><br />
          {error.confirmPassword && <span className='err'>{error.confirmPassword}</span>}
        </div>
        {status && <span className='err'>{status}</span>}
        <div className="d-grid gap-2 mt-3">
          <button type="submit" className="btn btn-primary" onClick={handleSubmit}>
            Register
          </button>
        </div>
        <div className="d-grid gap-2 mt-3">
          <button className="btn btn-secondary" onClick={handleLogin}>
            Already have an account?
          </button>
        </div>
        </div>
      </form>

        </div>
    )
  }
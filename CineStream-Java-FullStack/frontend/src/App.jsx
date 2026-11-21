import { useEffect, useState } from 'react'
import { Link, Routes, Route, useNavigate, useParams } from 'react-router-dom'
import api from './api'

function Header({ user, onLogout }) {
  return <header className="nav"><Link className="brand" to="/">CINE<span>STREAM</span></Link><nav><Link to="/">Home</Link><Link to="/my-list">My List</Link></nav>
    <div className="nav-right">{user ? <><span className="welcome">Hi, {user.name}</span><button className="outline small" onClick={onLogout}>Sign out</button></> : <Link className="button small" to="/login">Sign in</Link>}</div></header>
}
function MovieCard({ movie }) {
  return <Link className="card" to={`/title/${movie.id}`}><img src={movie.posterUrl} alt={movie.title}/><div className="card-info"><b>{movie.title}</b><span>{movie.releaseYear} · {movie.genre}</span></div></Link>
}
function Home() {
  const [movies,setMovies]=useState([]), [q,setQ]=useState(''), [genre,setGenre]=useState('all'), [error,setError]=useState('')
  useEffect(()=>{api.get('/movies',{params:{q,genre}}).then(r=>{setMovies(r.data);setError('')}).catch(()=>setError('Could not load titles. Is the Spring Boot backend running?'))},[q,genre])
  const featured=movies.find(m=>m.featured)||movies[0]
  return <><section className="hero" style={{backgroundImage:featured?`linear-gradient(90deg,#090b10 5%,rgba(9,11,16,.72) 48%,rgba(9,11,16,.12)),linear-gradient(0deg,#090b10,transparent 60%),url(${featured.backdropUrl})`:''}}>
    <div className="hero-content"><div className="eyebrow">C I N E S T R E A M&nbsp; O R I G I N A L</div><h1>{featured?.title||'Stories worth staying for.'}</h1><p>{featured?.description||'Discover your next favourite story.'}</p>
    {featured&&<div className="hero-actions"><Link className="button" to={`/title/${featured.id}`}>▶ Explore title</Link><span className="muted">{featured.releaseYear} &nbsp;·&nbsp; {featured.rating}</span></div>}</div></section>
    <main className="content"><div className="section-head"><div><p className="eyebrow">THE COLLECTION</p><h2>Explore titles</h2></div><div className="filters"><input value={q} onChange={e=>setQ(e.target.value)} placeholder="Search titles..."/><select value={genre} onChange={e=>setGenre(e.target.value)}><option value="all">All genres</option>{['Sci-Fi','Thriller','Documentary','Drama','Adventure'].map(g=><option key={g}>{g}</option>)}</select></div></div>
    {error?<p className="error">{error}</p>:<div className="grid">{movies.map(m=><MovieCard key={m.id} movie={m}/>)}</div>}{!error&&movies.length===0&&<p className="muted">No titles match your search.</p>}</main></>
}
function Auth({ mode, onAuth }) {
 const [form,setForm]=useState({name:'',email:'',password:''}),[error,setError]=useState(''),[busy,setBusy]=useState(false),nav=useNavigate()
 const submit=async e=>{e.preventDefault();setBusy(true);setError('');try{const {data}=await api.post(`/auth/${mode}`,form);localStorage.setItem('cinestream_token',data.token);onAuth({name:data.name,email:data.email});nav('/')}catch(err){setError(err.response?.data?.message||'Please check your details and try again.')}finally{setBusy(false)}}
 return <main className="auth-wrap"><form className="auth-card" onSubmit={submit}><p className="eyebrow">WELCOME TO CINESTREAM</p><h1>{mode==='register'?'Create your account':'Sign in'}</h1>{mode==='register'&&<input required placeholder="Full name" value={form.name} onChange={e=>setForm({...form,name:e.target.value})}/>}<input required type="email" placeholder="Email address" value={form.email} onChange={e=>setForm({...form,email:e.target.value})}/><input required minLength="8" type="password" placeholder="Password (min. 8 characters)" value={form.password} onChange={e=>setForm({...form,password:e.target.value})}/>{error&&<p className="error">{error}</p>}<button className="button full" disabled={busy}>{busy?'Please wait...':mode==='register'?'Create account':'Sign in'}</button><p className="muted">{mode==='register'?'Already have an account? ':'New to CineStream? '}<Link to={mode==='register'?'/login':'/register'}>{mode==='register'?'Sign in':'Create an account'}</Link></p></form></main>
}
function Details({user}) {
 const {id}=useParams(),[movie,setMovie]=useState(null),[saved,setSaved]=useState(false),[message,setMessage]=useState('')
 useEffect(()=>{api.get(`/movies/${id}`).then(r=>setMovie(r.data)).catch(()=>setMessage('Title not found.'));if(user)api.get('/watchlist').then(r=>setSaved(r.data.some(m=>String(m.id)===id))).catch(()=>{})},[id,user])
 const toggle=async()=>{if(!user){setMessage('Please sign in to manage your list.');return}try{if(saved)await api.delete(`/watchlist/${id}`);else await api.post(`/watchlist/${id}`);setSaved(!saved)}catch{setMessage('Could not update your list. Please sign in again.')}}
 if(!movie)return <main className="content">{message||'Loading title...'}</main>
 return <main className="detail" style={{backgroundImage:`linear-gradient(90deg,#090b10 5%,rgba(9,11,16,.72) 55%,rgba(9,11,16,.2)),linear-gradient(0deg,#090b10,transparent 70%),url(${movie.backdropUrl})`}}><div className="detail-inner"><img className="detail-poster" src={movie.posterUrl}/><div><p className="eyebrow">{movie.genre} · {movie.releaseYear}</p><h1>{movie.title}</h1><p className="description">{movie.description}</p><p className="muted">{movie.durationMinutes} min &nbsp;·&nbsp; {movie.rating}</p><div className="hero-actions"><a className="button" href="#player">▶ Play demo</a><button className="outline" onClick={toggle}>{saved?'✓ In My List':'+ My List'}</button></div>{message&&<p className="muted">{message}</p>}</div></div><section id="player" className="player-section"><h2>Watch preview</h2><video controls poster={movie.backdropUrl} src={movie.videoUrl}>Your browser does not support video.</video><p className="muted">Demo video only. Replace with media you own or are licensed to use.</p></section></main>
}
function MyList({user}) {
 const [movies,setMovies]=useState([]),[error,setError]=useState('')
 useEffect(()=>{if(user)api.get('/watchlist').then(r=>setMovies(r.data)).catch(()=>setError('Please sign in again.'))},[user])
 if(!user)return <main className="content"><h2>My List</h2><p>Please <Link to="/login">sign in</Link> to view your watchlist.</p></main>
 return <main className="content"><p className="eyebrow">YOUR COLLECTION</p><h2>My List</h2>{error&&<p className="error">{error}</p>}<div className="grid">{movies.map(m=><MovieCard key={m.id} movie={m}/>)}</div>{!movies.length&&!error&&<p className="muted">Your list is empty. Explore titles and save your favourites.</p>}</main>
}
export default function App(){
 const [user,setUser]=useState(()=>{try{return JSON.parse(localStorage.getItem('cinestream_user'))}catch{return null}})
 const auth=u=>{setUser(u);localStorage.setItem('cinestream_user',JSON.stringify(u))}
 const logout=()=>{localStorage.removeItem('cinestream_token');localStorage.removeItem('cinestream_user');setUser(null)}
 return <><Header user={user} onLogout={logout}/><Routes><Route path="/" element={<Home/>}/><Route path="/login" element={<Auth mode="login" onAuth={auth}/>}/><Route path="/register" element={<Auth mode="register" onAuth={auth}/>}/><Route path="/title/:id" element={<Details user={user}/>}/><Route path="/my-list" element={<MyList user={user}/>}/><Route path="*" element={<main className="content"><h2>Page not found</h2><Link to="/">Back home</Link></main>}/></Routes><footer>© 2026 CineStream · Built as a Java full-stack portfolio project</footer></>
}

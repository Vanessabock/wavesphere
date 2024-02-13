import githubMark from "./../assets/github-mark.png"

export const Login = () => {
    const login = () => {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin

        window.open(host + '/oauth2/authorization/github', '_self')
    }


    return (
        <div className="mx-auto flex flex-1 items-center justify-center pb-20">
            <button className="flex h-max items-center rounded-lg px-5 py-4 text-xl font-light" onClick={login}>
                <img className="mr-3 h-8" src={githubMark} alt="GitHub"/>
                Login with GitHub
            </button>
        </div>
    );
}
export default function checkAuth(props) {
  // props from react-router-dom
  return new Promise((resolve, reject) => {
    resolve({ user: { email: "user@gmai.com", name: "Test" } });
  });
}

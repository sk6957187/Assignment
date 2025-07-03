import { useNavigate, Outlet, useLocation } from 'react-router-dom';

function Converter() {
  const navigate = useNavigate();
  const location = useLocation();

  const isExcelPage = location.pathname.endsWith('/excel');
  const isSQLPage = location.pathname.endsWith('/sql');

  if (isExcelPage) {
    return <Outlet />;
  } else if(isSQLPage)
    return <Outlet />;
  return (
    <>
      <h3><u>Database Converter Page</u></h3>
      <button className="btn btn-success ms-3 me-3" onClick={() => navigate("excel")}>Read from Excel</button>
      <button className="btn btn-success" onClick={() => navigate("sql")}>Convert into SQL to Excel</button>
    </>
  );
}

export default Converter;

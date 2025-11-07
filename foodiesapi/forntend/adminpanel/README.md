# Admin Panel (adminpanel)

This is the admin-facing React app for the Foodies project. It provides interfaces to manage food items, view and manage orders, and perform administrative tasks.

This README explains how to run the admin panel in development, build it for production, and how to integrate it with the Spring Boot backend in the repository root.

## Quick overview

- Dev server for admin UI with live reload
- Production build: outputs a static `build/` folder that can be served by the backend or any static host
- Main admin features: create/edit/list food items, manage orders

## Prerequisites

- Node.js (14+ recommended) and npm
- A running backend (Spring Boot) to serve API endpoints during development or a configured API_BASE URL

## Run in development (PowerShell)

Open PowerShell and run:

```powershell
cd forntend\adminpanel
npm install
npm start
```

This starts the CRA dev server (default port 3000). If you run the customer app at the same time, adjust ports to avoid conflicts (CRA will prompt to run on another port).

## Environment (dev)

Set API base URL for the admin UI the same way as the customer app. Options:

- Add `proxy` to `package.json` (e.g. `"proxy": "http://localhost:8080"`) for development proxying
- Use env variables like `REACT_APP_API_BASE` and read it from service code

Example single-run in PowerShell:

```powershell
# $env:REACT_APP_API_BASE = 'http://localhost:8080/api'; npm start
```

## Build for production

```powershell
cd forntend\adminpanel
npm install
npm run build
```

The optimized static files are written to `forntend/adminpanel/build`. To serve the admin UI from the Spring Boot backend (single artifact), copy the contents of this `build` folder into `src/main/resources/static` (or into a subfolder if the backend expects both frontends). The repository already contains static assets under `src/main/resources/static` for convenience.

## Serving the built admin UI with the Spring Boot backend

1. Build admin UI: `npm run build`
2. Copy `forntend/adminpanel/build` to `src/main/resources/static` (or configure your build to place files there)
3. Build and run backend jar:

```powershell
.\mvnw.cmd clean package -DskipTests
java -jar target\foodiesapi-0.0.1-SNAPSHOT.jar
```

The Spring Boot app will serve the admin UI and API from the same host.

## Useful locations in the source

- App entry: `src/index.js` / `src/App.jsx`
- Add/list food pages: `src/pages/addFood/AddFood.jsx`, `src/pages/listFood/ListFood.jsx`
- Orders page: `src/pages/orders/Order.jsx`
- Services calling the backend: `src/service/FoodServices.jsx` (reused/shared) and `src/service/*`

## Tests

Run unit tests (if any) shipped with CRA:

```powershell
cd forntend\adminpanel
npm test
```

## Troubleshooting

- Port conflicts: if port 3000 is in use, CRA will prompt to run on another port (accept or stop the other process).
- CORS / 401 errors while using dev server: ensure the backend allows CORS from `localhost:3000`, or use the proxy in `package.json`.
- API base mismatch: set `REACT_APP_API_BASE` to your backend URL.

## Contributing / notes

- Use feature branches and include clear commit messages. Verify UI builds (`npm run build`) and that the backend can serve the built files when opening a PR.
- Do not commit secrets (API keys, Razorpay keys). Keep them in environment variables or secret stores.

---

Last updated: 2025-11-02
# Getting Started with Create React App

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can't go back!**

If you aren't satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you're on your own.

You don't have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn't feel obligated to use this feature. However we understand that this tool wouldn't be useful if you couldn't customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

### Code Splitting

This section has moved here: [https://facebook.github.io/create-react-app/docs/code-splitting](https://facebook.github.io/create-react-app/docs/code-splitting)

### Analyzing the Bundle Size

This section has moved here: [https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size](https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size)

### Making a Progressive Web App

This section has moved here: [https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app](https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app)

### Advanced Configuration

This section has moved here: [https://facebook.github.io/create-react-app/docs/advanced-configuration](https://facebook.github.io/create-react-app/docs/advanced-configuration)

### Deployment

This section has moved here: [https://facebook.github.io/create-react-app/docs/deployment](https://facebook.github.io/create-react-app/docs/deployment)

### `npm run build` fails to minify

This section has moved here: [https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify](https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify)

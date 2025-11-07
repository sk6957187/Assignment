# Foodies (customer app)

This is the customer-facing React app for the Foodies project. It was bootstrapped with Create React App and is located at `forntend/foodies`.

This README explains how to run the app in development, build it for production, and how it integrates with the Spring Boot backend in the repository root.

## Quick overview

- Dev server: fast local development with live reload
- Production build: outputs a static `build/` folder which can be served by the backend or any static server
- API: the app calls the backend REST API (see `src/service` and `src/context/StoreContext.jsx` for where API requests are made)

## Prerequisites

- Node.js (14+ recommended) and npm
- A running backend (Spring Boot) to serve API endpoints during development or a configured API_BASE URL

## Run in development (PowerShell)

Open PowerShell and run:

```powershell
cd forntend\foodies
npm install
npm start
```

This starts the CRA dev server (default port 3000). The UI will reload when you change files.

If your backend runs on a different port (for example :8080), ensure requests are proxied to that backend. You can either:

- add a `proxy` entry to `package.json` (e.g. `"proxy": "http://localhost:8080"`) — convenient for development; or
- set an environment variable and configure API base URLs in `src/service/FoodServices.jsx` / `Authservice.jsx`.

## Environment (dev)

You can set environment variables for the React build using `.env` files or directly in PowerShell. Example `.env.development` keys you might use:

- REACT_APP_API_BASE=http://localhost:8080/api

Use PowerShell to set for a single run:

```powershell
# $env:REACT_APP_API_BASE = 'http://localhost:8080/api'; npm start
```

## Build for production

```powershell
cd forntend\foodies
npm install
npm run build
```

The optimized static files are written to `forntend/foodies/build`. To serve the UI from the Spring Boot backend (single artifact), copy the contents of the `build` folder into `src/main/resources/static` of the backend project before packaging the jar. The repository already contains a copy of built assets in `src/main/resources/static` for convenience.

## Serving the built app with the Spring Boot backend

1. Build frontend: `npm run build`
2. Copy `forntend/foodies/build` into `src/main/resources/static` (or configure your build to place files there)
3. Build backend jar and run:

```powershell
.\mvnw.cmd clean package -DskipTests
java -jar target\foodiesapi-0.0.1-SNAPSHOT.jar
```

The Spring Boot app will serve the static files and API from the same host.

## Useful locations in the source

- App entry: `src/index.js` / `src/App.js`
- Pages: `src/pages/*` (e.g., `src/pages/myOrders/MyOrders.jsx`)
- Services calling the backend: `src/service/FoodServices.jsx`, `src/service/Authservice.jsx`, `src/service/cartService.js`
- Global state & context: `src/context/StoreContext.jsx`

## Tests

Run unit tests shipped with CRA:

```powershell
cd forntend\foodies
npm test
```

## Troubleshooting

- CORS / 401 errors while using dev server: ensure the backend allows CORS from `localhost:3000`, or use proxy in `package.json`.
- API base mismatch: set `REACT_APP_API_BASE` to your backend URL.
- Static UI not found after packaging: confirm the built files were copied into `src/main/resources/static` before packaging the jar.

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

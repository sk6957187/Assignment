# Features — Foodies

This file lists and groups the main features implemented in this repository (backend and both frontends), plus integrations and where to find the related code. Use it as a quick reference when adding features, debugging, or writing docs.

## Backend (Spring Boot)

- Authentication & Authorization
  - JWT-based authentication and security filters
  - Login / registration endpoints
  - Code: `src/main/java/in/nayak/foodiesapi/filters/JwtAuthenticationFilter.java`, `util/JwtUtil.java`, `config/SecurityConfig.java`, `controller/AuthController.java`

- User management
  - User registration, profile handling
  - Code: `controller/UserController.java`, `service/UserService.java`, `entity/UserEntity.java`, `repository/UserRepository.java`, `io/UserRequest.java`, `io/UserResponse.java`

- Food catalogue
  - CRUD operations for food items (list, create, update, delete)
  - Food listing / search / category support used by front-end pages
  - Code: `controller/FoodController.java`, `service/FoodService.java`, `entity/FoodEntity.java`, `repository/FoodRepository.java`, `io/FoodRequest.java`, `io/FoodResponse.java`

- Cart management
  - Add to cart, remove from cart, view cart operations
  - Maintains cart items per user
  - Code: `controller/CartController.java`, `service/CartService.java`, `entity/CartEntity.java`, `repository/CartRespository.java`, `io/CartRequest.java`, `io/CartResponse.java`

- Orders
  - Place orders, view order details and order history
  - Order items / totals and order status management
  - Code: `controller/OrderController.java`, `service/OrderService.java`, `entity/OrderEntity.java`, `entity/OrderItem.java`, `repository/OrderRepository.java`, `io/OrderRequest.java`, `io/OrderResponse.java`, `io/OrderItem.java`

- SPA static serving
  - A controller to route SPA client-side routes to the static index file
  - Code: `controller/SpaController.java` and `src/main/resources/static` assets

- AWS integration (optional in project)
  - AWS config present (likely for S3 or other AWS services)
  - Code: `config/AWSConfig.java`

- Persistence & Data access
  - JPA Entities, Repositories, and standard Spring Data patterns
  - Code: `entity/`, `repository/`

- Tests
  - Backend unit tests under `test/java/in/nayak/foodiesapi`

## Customer front-end (React — `forntend/foodies`)

- Authentication & user flows
  - Login, registration, JWT storage/management in client
  - Code: `src/pages/login`, `src/service/Authservice.jsx`, `src/context/StoreContext.jsx`

- Browse & discover food
  - Home, explore and food details pages
  - Search and categories (as implemented in explore pages)
  - Code: `src/pages/home`, `src/pages/exploreFood`, `src/pages/foodDetails`, `src/components/*`

- Cart & checkout
  - Add/remove items, view cart, place order
  - Place order flows including order summary and checkout
  - Code: `src/pages/cart/Cart.jsx`, `src/pages/placeOrder/PlaceOrder.jsx`, `src/service/cartService.js`

- Orders / My orders
  - View order history and order details
  - Code: `src/pages/myOrders/MyOrders.jsx`, `src/pages/myOrders/*`

- Responsive UI and static assets
  - Built with Create React App and uses bundled CSS/JS libs in `public/static/libs`

## Admin front-end (React — `forntend/adminpanel`)

- Food management
  - Add new food items (with images/metadata), edit and list food
  - Code: `src/pages/addFood/AddFood.jsx`, `src/pages/listFood/ListFood.jsx`, `src/components/*`

- Order management
  - Admin can view/manage orders from admin UI
  - Code: `src/pages/orders/Order.jsx`

- Built and deployable separately or copied into backend static folder for unified deployment

## Integrations

- Payment gateway
  - Razorpay keys presence indicates payment integration is supported or expected (`rzp-key.csv`). Payment flows are commonly part of `PlaceOrder` and server order endpoints.

- AWS
  - AWS configuration class present; may be used for file storage or other AWS services. See `config/AWSConfig.java`.

- JWT
  - Backend issues JWTs; client stores and sends them on protected API calls.

## DTOs & request/response shapes

- DTOs for request/response objects live in `src/main/java/in/nayak/foodiesapi/io/` and include:
  - `AuthenticationRequest`, `AuthenticationResponse`
  - `FoodRequest`, `FoodResponse`
  - `CartRequest`, `CartResponse`
  - `OrderRequest`, `OrderResponse`, `OrderItem`
  - `UserRequest`, `UserResponse`

These DTOs describe the API contract — inspect them for exact JSON shapes used by the frontends.

## Where to find related code quickly

- Backend entrypoint: `src/main/java/in/nayak/foodiesapi/FoodiesapiApplication.java`
- Controllers: `src/main/java/in/nayak/foodiesapi/controller`
- Services: `src/main/java/in/nayak/foodiesapi/service`
- Entities: `src/main/java/in/nayak/foodiesapi/entity`
- Repositories: `src/main/java/in/nayak/foodiesapi/repository`
- Frontend customer app: `forntend/foodies/src`
- Frontend admin panel: `forntend/adminpanel/src`

## Notes & assumptions

- Some integrations (AWS, payment) may require keys or additional setup. Check `application.properties` and any `.env` files for environment-specific configuration.
- UI and backend builds are present in `src/main/resources/static` and `target/static` for convenience — if you modify frontend code, rebuild and copy outputs to those locations before packaging the backend jar if you want a single artifact.

## Next suggestions

- Add a short API reference (endpoints + example requests) into `docs/` or `README.md` for developer convenience.
- Add example `.env.example` (backend and frontend) showing environment variables required for local dev without sharing secrets.

---

Last updated: 2025-11-02

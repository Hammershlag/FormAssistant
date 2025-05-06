# Build React app
FROM node:18 as build
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build

# Serve with Nginx
FROM nginx:alpine
LABEL image.name="form-assistant-frontend:latest"
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80

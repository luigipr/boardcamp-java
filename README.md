# BoardCamp Java

BoardCamp Java is a project for managing rental transactions of board games. It provides APIs for creating and managing customers, games, and rentals.

## Getting Started

To get started with the project, follow these steps:

**Clone the repository:**

git clone https://github.com/luigipr/boardcamp-java.git

**Navigate to the project directory:**

cd boardcamp-java

**Build the project:**

./mvnw clean package

**Run the application:**

java -jar target/boardcamp-java.jar

**Access the APIs:**

You can access the APIs at http://localhost:8080.


## Usage

Endpoints

GET /customers/{id}: Retrieve customer details by ID.

POST /customers: Create a new customer.

GET /games: Retrieve all games.

POST /games: Create a new game.

GET /rentals: Retrieve all rentals.

POST /rentals: Rent a game.

PUT /rentals/{id}/return: Returns a game.

## Sample Requests

**Create a new customer:**

POST /customers

{
  "name": "John Doe",
  "cpf": "12345678900"
}

**Rent a game:**

POST /rentals


{
  "customerId": 1,
  "gameId": 1,
  "daysRented": 5
}

## Testing
To run the tests, execute the following command:

./mvnw test

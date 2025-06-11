#!/usr/bin/env python

from test_utils import APIClient, TestAssertions
from test_runner import TestRunner
import time

# This dictionary stores reusable credentials and book details.
test_data = {
    "admin": None,
    "user": None,
    "books": []
}

client = APIClient()
assertions = TestAssertions()

def create_admin_account():
    """Create and verify admin account"""
    timestamp = int(time.time() * 1000)
    admin_creds = {
        "email": f"admin.test.{timestamp}@example.com",
        "password": "admin123456",
        "firstName": "AdminTest",
        "lastName": "UserTest",
        "role": "ADMIN"
    }
    response = client.post("auth/signup", json=admin_creds)
    assertions.assert_status_code(response, 201)
    test_data["admin"] = {**admin_creds, "id": response.json()["id"]}

    login_response = client.post("auth/login", json={
        "email": admin_creds["email"],
        "password": admin_creds["password"]
    })
    assertions.assert_status_code(login_response, 200)
    assertions.assert_json_field(login_response, "role", "ADMIN")

def create_user_account():
    """Create and verify regular user account"""
    timestamp = int(time.time() * 1000)
    user_creds = {
        "email": f"user.test.{timestamp}@example.com",
        "password": "user123456",
        "firstName": "RegularTest",
        "lastName": "UserTest"
    }
    response = client.post("auth/signup", json=user_creds)
    assertions.assert_status_code(response, 201)
    test_data["user"] = {**user_creds, "id": response.json()["id"]}

    login_response = client.post("auth/login", json={
        "email": user_creds["email"],
        "password": user_creds["password"]
    })
    assertions.assert_status_code(login_response, 200)
    assertions.assert_json_field(login_response, "role", "USER")

def setup_test_books():
    """Create initial test books for admin"""
    for i in range(1, 4):
        book_data = {
            "title": f"Test Book {time.time() * 1000}-{i}",
            "author": "Test Author",
            "genre": "FICTION",
            "availabilityStatus": "AVAILABLE"
        }
        response = client.post("books",
                            json=book_data,
                            auth=(test_data["admin"]["email"], test_data["admin"]["password"]))
        assertions.assert_status_code(response, 201)
        test_data["books"].append(response.json())

def admin_create_delete_book():
    """Test admin can create and delete books"""
    book_title = f"Test Book {time.time() * 1000}"
    response = client.post("books",
                        json={
                            "title": book_title,
                            "author": "Test Author",
                            "genre": "FICTION",
                            "availabilityStatus": "AVAILABLE"
                        },
                        auth=(test_data["admin"]["email"], test_data["admin"]["password"]))
    assertions.assert_status_code(response, 201)
    test_data["books"].append(response.json())

    delete_response = client.delete(f"books/{response.json()['id']}",
                                auth=(test_data["admin"]["email"], test_data["admin"]["password"]))
    assertions.assert_status_code(delete_response, 204)

def create_rental_test_books():
    """Create books specifically for rental tests"""
    for i in range(1, 4):
        book_data = {
            "title": f"Test Book {time.time() * 1000}-{i}",
            "author": "Test Author",
            "genre": "FICTION",
            "availabilityStatus": "AVAILABLE"
        }
        response = client.post("books",
                            json=book_data,
                            auth=(test_data["admin"]["email"], test_data["admin"]["password"]))
        assertions.assert_status_code(response, 201)
        test_data["books"].append(response.json())

def admin_update_book():
    """Test admin can update an existing book"""
    original_title = f"Test Book {int(time.time())}"
    create_response = client.post("books",
                              json={
                                  "title": original_title,
                                  "author": "Test Author",
                                  "genre": "FICTION",
                                  "availabilityStatus": "AVAILABLE"
                              },
                              auth=(test_data["admin"]["email"], test_data["admin"]["password"]))
    assertions.assert_status_code(create_response, 201)
    created_book = create_response.json()

    updated_title = f"Updated {int(time.time()) % 1000}"
    update_response = client.put(f"books/{created_book['id']}",
                              json={
                                  "id": created_book['id'],
                                  "title": updated_title,
                                  "author": "Updated Author",
                                  "genre": "NON_FICTION",
                                  "availabilityStatus": "AVAILABLE"
                              },
                              auth=(test_data["admin"]["email"], test_data["admin"]["password"]))
    if update_response.status_code == 500:
        print(f"\nError updating book: {update_response.text}")

    assertions.assert_status_code(update_response, 200)
    assertions.assert_json_field(update_response, "title", updated_title)
    assertions.assert_json_field(update_response, "author", "Updated Author")
    assertions.assert_json_field(update_response, "genre", "NON_FICTION")

def user_cannot_update_books():
    """Test that regular users are forbidden from updating books"""
    book_to_update = test_data["books"][0]
    response = client.put(f"books/{book_to_update['id']}",
                       json={
                           "title": "Unauthorized Update",
                           "author": "User Author",
                           "genre": "FICTION",
                           "availabilityStatus": "AVAILABLE"
                       },
                       auth=(test_data["user"]["email"], test_data["user"]["password"]))
    assertions.assert_status_code(response, 403)

def user_cannot_create_books():
    """Test that regular users are forbidden from creating books"""
    response = client.post("books",
                        json={
                            "title": f"Test Book {time.time() * 1000}",
                            "author": "Test Author",
                            "genre": "FICTION",
                            "availabilityStatus": "AVAILABLE"
                        },
                        auth=(test_data["user"]["email"], test_data["user"]["password"]))
    assertions.assert_status_code(response, 403)

def user_cannot_delete_books():
    """Test that regular users are forbidden from deleting books"""
    book_to_delete = test_data["books"][0]
    response = client.delete(f"books/{book_to_delete['id']}",
                          auth=(test_data["user"]["email"], test_data["user"]["password"]))
    assertions.assert_status_code(response, 403)

def user_can_rent_book():
    """Test that users can rent available books"""
    book_to_rent = test_data["books"][0]
    response = client.post(f"rentals/users/{test_data['user']['id']}/books/{book_to_rent['id']}",
                        auth=(test_data["user"]["email"], test_data["user"]["password"]))
    assertions.assert_status_code(response, 201)

def rental_limit_enforced():
    """Test that rental limit is enforced"""
    second_book = test_data["books"][1]
    response = client.post(f"rentals/users/{test_data['user']['id']}/books/{second_book['id']}",
                        auth=(test_data["user"]["email"], test_data["user"]["password"]))
    assertions.assert_status_code(response, 201)
    
    third_book = test_data["books"][2]
    response = client.post(f"rentals/users/{test_data['user']['id']}/books/{third_book['id']}",
                        auth=(test_data["user"]["email"], test_data["user"]["password"]))
    assertions.assert_status_code(response, 400)

def return_rented_book():
    """Test that users can return rented books"""
    response = client.get(f"rentals/active-rentals/users/{test_data['user']['id']}",
                       auth=(test_data["user"]["email"], test_data["user"]["password"]))
    assertions.assert_status_code(response, 200)
    rental_id = response.json()[0]["id"]
    return_response = client.put(f"rentals/{rental_id}",
                              auth=(test_data["user"]["email"], test_data["user"]["password"]))
    assertions.assert_status_code(return_response, 204)

tests = {
    "Create Admin Account": create_admin_account,
    "Create User Account": create_user_account,
    "Setup Test Books": setup_test_books,
    "Admin Can Create And Delete Book": admin_create_delete_book,
    "Create Books For Rental Tests": create_rental_test_books,
    "Admin Can Update Book": admin_update_book,
    "User Cannot Update Books": user_cannot_update_books,
    "User Cannot Create Books": user_cannot_create_books,
    "User Cannot Delete Books": user_cannot_delete_books,
    "User Can Rent Book": user_can_rent_book,
    "Rental Limit Is Enforced": rental_limit_enforced,
    "User Can Return Rented Book": return_rented_book
}

if __name__ == "__main__":
    TestRunner().run(tests)
# Lab Report System

This project is developed using Java 17. To run the project, follow the steps below.

## Prerequisites

- Java 17
- Maven

## Setup

1. **Clone the repository:**
    ```bash
    git clone <repository_url>
    ```

2. **Navigate to the project directory:**
    ```bash
    cd <project_path>
    ```

3. **Build the project using Maven:**
    ```bash
    mvnw.cmd clean install
    ```

    If you encounter an error related to `JAVA_HOME` environment variable, make sure it is correctly set. Here’s how you can set it:

    ### Setting JAVA_HOME Environment Variable

    **Windows:**

    1. Open the Start Menu and search for "Environment Variables".
    2. Click on "Edit the system environment variables".
    3. In the System Properties window, click on the "Environment Variables" button.
    4. In the Environment Variables window, click on the "New" button under the "System variables" section.
    5. Enter `JAVA_HOME` as the Variable name.
    6. Enter the path to your JDK installation as the Variable value (e.g., `C:\Program Files\Java\jdk-17`).
    7. Click "OK" to save the changes.

    **Linux/MacOS:**

    1. Open a terminal.
    2. Open the `.bashrc`, `.bash_profile`, or `.zshrc` file in a text editor (depending on your shell).
    3. Add the following line to the file:
        ```bash
        export JAVA_HOME=/path/to/your/jdk
        ```
    4. Save the file and run `source ~/.bashrc` or `source ~/.bash_profile` or `source ~/.zshrc` to apply the changes.

4. **Run the generated JAR file:**

    After the build is successful, navigate to the `target` directory and run the JAR file using the following command:

    ```bash
    java -jar target/<your-project-name>-1.0-SNAPSHOT.jar
    ```

That's it! Your project should now be up and running. If you encounter any issues, please refer to the documentation or seek help from the community.

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="./layout/admin.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <div class="container">
    <h1>Create Employee</h1>

    <form action="" method="post" class="form-container">
      <div class="form-group">
        <label for="fullName">Full Name:</label>
        <input type="text" class="form-control" id="fullName" name="fullName" required>
      </div>

      <div class="form-group">
        <label for="birthday">Birthday:</label>
        <input type="date" class="form-control" id="birthday" name="birthday" required>
      </div>

      <div class="form-group">
        <label for="address">Address:</label>
        <input type="text" class="form-control" id="address" name="address" required>
      </div>

      <div class="form-group">
        <label for="position">Position:</label>
        <input type="text" class="form-control" id="position" name="position" required>
      </div>

      <div class="form-group">
        <label for="department">Department:</label>
        <input type="text" class="form-control" id="department" name="department" required>
      </div>

      <button type="submit" class="btn btn-primary">Create</button>
      <button type="reset" class="btn btn-danger">Reset</button>

    </form>

  </div>
</body>
</html>

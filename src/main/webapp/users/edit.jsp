<%@ include file="/header.jsp" %>

<div class="container-fluid">

  <!-- Page Heading -->
  <div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h1 class="h3 mb-0 text-gray-800">UserCRUD</h1>
    <a href="<c:url value="/user/list"/>" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
            class="fas fa-download fa-sm text-white-50"></i> Lista uzytkownikow</a>
  </div>

  <!-- Content Row -->
  <div class="row">

    <!-- Content Column -->
    <div class="col-lg-12 mb-4">
      <!-- DataTales Example -->
      <div class="card shadow mb-4">
        <div class="card-header py-3">
          <h6 class="m-0 font-weight-bold text-primary">Edycja uzytkownika</h6>
        </div>
        <div class="card-body">
          <form action='/user/edit' method="post"/>
          <input type="hidden" name="id" value="${user.id}">
          <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" value="${user.email}" class="form-control">
          </div>
          <div class="mb-3">
            <label class="form-label">Username</label>
            <input type="text" name="username" value="${user.userName}" class="form-control">
          </div>
          <div class="mb-3">
            <label class="form-label">Password</label>
            <input type="password" name="password" placeholder="Haslo uzytkownika" class="form-control">
          </div>
          <button type="submit" class="btn btn-primary">Edytuj</button>
          </form>
        </div>
      </div>
      <!-- Nav Item - Dashboard -->
    </div>
  </div>

</div>
<!-- /.container-fluid -->

<%@ include file="/footer.jsp" %>
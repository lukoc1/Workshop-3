<%@ include file="/header.jsp" %>

<div class="container-fluid">

  <!-- Page Heading -->
  <div class="d-sm-flex align-items-center justify-content-between mb-4">
    <h1 class="h3 mb-0 text-gray-800">UserCRUD</h1>
    <a href="<c:url value="/user/add"/>" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
            class="fas fa-download fa-sm text-white-50"></i> Dodaj uzytkownika</a>
  </div>

  <!-- Content Row -->
  <div class="row">

    <!-- Content Column -->
    <div class="col-lg-12 mb-4">
      <!-- DataTales Example -->
      <div class="card shadow mb-4">
        <div class="card-header py-3">
          <h6 class="m-0 font-weight-bold text-primary">Szczegoly uzytkownika</h6>
            </div>
            <div class="card-body">
              <!-- Divider -->
              <hr class="sidebar-divider d-none d-md-block">

              <div class="row mb-3">
                <div class="col-4"><strong>Id</strong></div>
                <div class="col-8">${user.id}</div>
              </div>

              <!-- Divider -->
              <hr class="sidebar-divider d-none d-md-block">

              <div class="row mb-3">
                <div class="col-4"><strong>Nazwa uzytkownika</strong></div>
                <div class="col-8">${user.userName}</div>
              </div>

              <!-- Divider -->
              <hr class="sidebar-divider d-none d-md-block">

              <div class="row mb-3">
              <div class="col-4"><strong>Email</strong></div>
                <div class="col-8">${user.email}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- Nav Item - Dashboard -->
    </div>
  </div>

</div>
<!-- /.container-fluid -->

<%@ include file="/footer.jsp" %>
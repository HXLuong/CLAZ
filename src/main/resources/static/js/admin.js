const sidebarToggle = document.querySelector("#sidebar-toggle");
sidebarToggle.addEventListener("click", function() {
	document.querySelector("#sidebar").classList.toggle("collapsed");
});

document.querySelector(".theme-toggle").addEventListener("click", () => {
	toggleLocalStorage();
	toggleRootClass();
});

function toggleRootClass() {
	const current = document.documentElement.getAttribute('data-bs-theme');
	const inverted = current == 'dark' ? 'light' : 'dark';
	document.documentElement.setAttribute('data-bs-theme', inverted);
}

function toggleLocalStorage() {
	if (isLight()) {
		localStorage.removeItem("light");
	} else {
		localStorage.setItem("light", "set");
	}
}

function isLight() {
	return localStorage.getItem("light");
}

if (isLight()) {
	toggleRootClass();
}

// chart //

google.charts.load('current', { 'packages': ['corechart'] });
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
	const data = google.visualization.arrayToDataTable([
		['Contry', 'Mhl'],
		['Tháng 1', 10],
		['Tháng 2', 12],
		['Tháng 3', 11],
		['Tháng 4', 13],
		['Tháng 5', 14],
		['Tháng 6', 15],
		['Tháng 7', 15],
		['Tháng 8', 15],
		['Tháng 9', 15],
		['Tháng 10', 15],
		['Tháng 11', 15],
		['Tháng 12', 15]
	]);

	const options = {
		title: 'Doanh Thu 2024',
		backgroundColor: { fill: 'transparent' },
		titleTextStyle: {
			color: '#ffffff',
			fontSize: 18,
			bold: true,
			italic: false
		},
		legend: {
			alignment: 'center',  // Căn giữa chú thích
			textStyle: { color: '#ffffff' }
		},
		pieSliceTextStyle: { color: '#ffffff' },  // Đổi màu chữ trên biểu đồ pie
		chartArea: { width: '100%', height: '80%' },  // Vùng hiển thị biểu đồ
	};

	const chart = new google.visualization.PieChart(document.getElementById('myChart'));
	chart.draw(data, options);
}

window.onresize = function() {
	drawChart();
};
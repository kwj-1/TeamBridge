async function commute() {
    const btn = document.getElementById('btnCommute');
    const action = btn.innerText.trim() === '출근하기' ? 'checkIn' : 'checkOut';

    try {
        const response = await fetch(`/attendance/${action}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' }
        });
        
        const result = await response.json();

        if (result.success) {
            showToast(result.message, 'success');
            
            if (action === 'checkIn') {
                // 출근 성공 시 -> 버튼을 퇴근하기로 변경
                btn.innerText = '퇴근하기';
                btn.className = 'btn btn-danger'; // 클래스 변경(필요시)
            } else {
                // 퇴근 성공 시 -> 버튼을 비활성화하거나 다른 상태로 변경
                btn.innerText = '퇴근 완료';
                btn.disabled = true;
                btn.className = 'btn btn-secondary';
            }
            // --------------------------------------
        } else {
            showToast(result.message, 'danger');
        }
    } catch (error) {
        showToast('서버 통신 중 오류가 발생했습니다.', 'danger');
    }
}
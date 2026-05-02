import styled from "styled-components"

const OptionsContainer = styled.ul`
    display: flex;
`
const Option = styled.li`
    font-size: 1.2rem;
    min-width: 120px;
    height: 100%;
    justify-content: center;
    align-items: center;
    text-align: center;
    padding: 0 5px;
    cursor: pointer;
`



const textOptions = ['CATEGORY', 'FAVORITES', 'MY SHELF']
function OptionsHeader() {
    return (
        <OptionsContainer>
            { textOptions.map( (text) => (
                <Option><p>{text}</p></Option>
            ) ) }
        </OptionsContainer>
    )
}
export default OptionsHeader

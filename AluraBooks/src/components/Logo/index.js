import logo from '../../images/logo.svg'
import styled from 'styled-components'

const LogoContainer = styled.div`
  display: flex;
  font-size: 30px;
`

const ContainerImage = styled.img`
  margin-right: 1rem;
`

function Logo() {
    return (
      <LogoContainer>
        <ContainerImage 
        src={logo} 
        alt='logo'
        />
        <p><strong> AluraBooks </strong></p>
      </LogoContainer>
    )
}
export default Logo
import { UploadFile as UploadFileIcon } from '@mui/icons-material'
import { Card, CardContent, Container } from '@mui/material'
import PageHeader from '../shell/PageHeader'
import DropZone from './DropZone'

const Import = () => {
  return (
    <Container maxWidth={false}>
      <PageHeader
        title={'Import'}
        breadcrumbs={[
          {
            icon: <UploadFileIcon />,
            link: '/import/',
            title: 'Import'
          }
        ]}
        description={'Import or submit Patient records to MPI'}
      />
      <Card variant="outlined">
        <CardContent sx={{ minWidth: { xs: '100%', md: '100%' } }}>
          <DropZone />
        </CardContent>
      </Card>
    </Container>
  )
}

export default Import
